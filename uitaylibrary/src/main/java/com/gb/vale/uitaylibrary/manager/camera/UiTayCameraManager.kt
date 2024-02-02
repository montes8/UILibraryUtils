package com.gb.vale.uitaylibrary.manager.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.gb.vale.uitaylibrary.manager.permission.UiTayPermissionManager
import com.gb.vale.uitaylibrary.utils.UI_TAY_EMPTY
import com.gb.vale.uitaylibrary.utils.uiTayReduceBitmapSize
import com.gb.vale.uitaylibrary.utils.uiTayRotateIfNeeded
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Calendar

class UiTayCameraManager (
    private val context: AppCompatActivity,
    private val namePath: String,
    private val listener: CameraControllerListener?
){

    private var pictureFileName = UI_TAY_EMPTY
    private lateinit var picturePathTemp: String
    private lateinit var pictureNameTemp: String
    private var cameraRequest: ActivityResultLauncher<Intent>? = null

    private val permissionManager: UiTayPermissionManager =
        UiTayPermissionManager(context, onDeny = {
            listener?.onCameraPermissionDenied()
        })

    init {
        registerIntent()
    }


    fun doCamera(){
        permissionManager.requestPermissions(
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            var pictureFile: File? = null
            try {
                pictureFile = createPictureFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (pictureFile != null) {
                val pictureUri = FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName,
                    pictureFile
                )
                chooseCameraOptions(pictureUri)
            }
        }

    }

    private fun registerIntent(){
        cameraRequest = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                onActivityResult(result.resultCode, result.data) }
        }
    }

    private fun onActivityResult(resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
            val storageDir = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                UI_TAY_EMPTY + namePath
            )
            if(data == null) {
                putImageCamera(storageDir, externalFilesDir)
            } else {
                putImageIntoFolder(data, externalFilesDir, storageDir)
            }
        }
    }


    private fun putImageCamera(storageDir: File, externalFilesDir: String){
        val directory = File(storageDir.path)
        val files = directory.listFiles()
        if(files != null) {
            for (i in files.indices) {
                val imgFile = File(storageDir.path + "/" + files[i].name)
                if(imgFile.name == pictureNameTemp && imgFile.exists()) {
                    val myBitmap = context.uiTayRotateIfNeeded(uiTayReduceBitmapSize(imgFile), Uri.fromFile(imgFile))
                    try {
                        FileOutputStream(picturePathTemp).use { out -> myBitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            50,
                            out
                        ) }
                        val path = "$externalFilesDir/$namePath/$pictureFileName.jpg"
                        val file = File(path)
                        val imgGallery = BitmapFactory.decodeFile(file.absolutePath)
                        Log.d("onActivityResult",path)
                        listener?.onGetImageCameraCompleted(
                            path, context.uiTayRotateIfNeeded(imgGallery, Uri.fromFile(file))
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.d("onActivityResult", "onActivityResult: $e")
                    }
                }
            }
        }
    }

    private fun putImageIntoFolder(data: Intent?, externalFilesDir: String, storageDir: File) {
        try {
            val calendar = Calendar.getInstance()
            val pictureFileName = calendar.timeInMillis.toString()
            val photoFile = File(storageDir.path + "/" + pictureFileName + ".jpg")
            val inputStream: InputStream? = data?.data?.let { context.contentResolver.openInputStream(
                it
            ) }
            Log.d("onActivityResult","inputStream")
            val fOutputStream = FileOutputStream(photoFile)
            copyStream(inputStream, fOutputStream)
            fOutputStream.close()
            inputStream?.close()
            Log.d("onActivityResult","close")
            val bitmap = context.uiTayRotateIfNeeded(uiTayReduceBitmapSize(photoFile), Uri.fromFile(photoFile))
            FileOutputStream(photoFile).use { out -> bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                50,
                out
            ) }
            val path = "$externalFilesDir/$namePath/$pictureFileName.jpg"
            val file = File(path)
            val imgGallery = BitmapFactory.decodeFile(file.absolutePath)
            listener?.onGetImageCameraCompleted(path, imgGallery)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun copyStream(input: InputStream?, output: OutputStream?) {
        val buffer = ByteArray(1024)
        var bytesRead: Int
        do {
            input?.apply {
                bytesRead = this.read(buffer)
                if(bytesRead == -1)
                    return
                output?.write(buffer, 0, bytesRead)
            }
        } while (true)
    }

    private fun createPictureFile(): File {
        val calendar = Calendar.getInstance()
        pictureFileName = calendar.timeInMillis.toString()
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val picture = File("$storageDir/$namePath", "$pictureFileName.jpg")
        val newPath = File("$storageDir/$namePath")
        if(!newPath.exists()) {
            newPath.mkdirs()
        }
        picturePathTemp = picture.absolutePath
        pictureNameTemp = picture.name
        return picture
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun chooseCameraOptions(outputFileUri: Uri) {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        cameraRequest?.launch(captureIntent)
    }


    interface CameraControllerListener {
        fun onCameraPermissionDenied()
        fun onGetImageCameraCompleted(path: String, img: Bitmap)
    }
}