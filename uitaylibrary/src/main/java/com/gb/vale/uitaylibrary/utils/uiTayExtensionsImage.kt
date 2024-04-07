package com.gb.vale.uitaylibrary.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import com.gb.vale.uitaylibrary.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar

fun uiTayReduceBitmapSize(imageFilePath: File): Bitmap {
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imageFilePath.absolutePath, bmOptions)
    bmOptions.inSampleSize = uiTayCalculateInSampleSize(bmOptions)
    bmOptions.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(imageFilePath.absolutePath, bmOptions)
}


fun uiTayCalculateInSampleSize(bmOptions: BitmapFactory.Options): Int {
    val photoWidth = bmOptions.outWidth
    val photoHeight = bmOptions.outHeight
    var scaleFactor = 1
    if (photoWidth > 1000 || photoHeight > 1000) {
        val halfPhotoWidth = photoWidth / 2
        val halfPhotoHeight = photoHeight / 2
        while (halfPhotoWidth / scaleFactor >= 500 && halfPhotoHeight / scaleFactor >= 500) {
            scaleFactor *= 2
        }
    }
    return scaleFactor
}


fun uiTayImageBitmapPath(path : String):Bitmap?{
    val pathUri = File(path)
    return BitmapFactory.decodeFile(pathUri.absolutePath)
}

fun Context.uiTayCreatePictureFile(nameFile:String = "imagesave"): File {
    val pictureFileName: String
    val calendar = Calendar.getInstance()
    pictureFileName = calendar.timeInMillis.toString()
    val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val picture = File("$storageDir/$nameFile", "$pictureFileName.jpg")
    val newPath = File("$storageDir$nameFile")
    if(!newPath.exists()) {
        newPath.mkdirs()
    }
    return picture
}

fun  Context.uiTaySaveImagen (nameFile : File, img : Bitmap, nameImage : String,
                      toast:Boolean=false,message:String = UI_TAY_EMPTY):String{
    val myPath = File(nameFile, "$nameImage.png")

    val fos: FileOutputStream?
    try{
        fos = FileOutputStream(myPath)
        img.compress(Bitmap.CompressFormat.JPEG, 10, fos)
        fos.flush()
        if (toast)this.uiTayShowToast(message)
    }catch (ex : FileNotFoundException){
        ex.printStackTrace()
        if (toast)this.uiTayShowToast(this.getString(R.string.tay_ui_error_pdf_link))
    }catch (ex: IOException){
        ex.printStackTrace()
        if (toast)this.uiTayShowToast(this.getString(R.string.tay_ui_error_pdf_link))
    }
    return myPath.absolutePath
}

fun Context.uiTayRotateIfNeeded(bitmap: Bitmap, uri: Uri) : Bitmap {
    val exitInt = this.contentResolver.openInputStream(uri)?.let { ExifInterface(it) }
    return when(exitInt?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> {
            uiTayRotateImages(bitmap, 90)
        }
        ExifInterface.ORIENTATION_ROTATE_180 -> {
            uiTayRotateImages(bitmap, 180)
        }
        ExifInterface.ORIENTATION_ROTATE_270 -> {
            uiTayRotateImages(bitmap, 270)
        }
        else -> {
            bitmap
        }
    }
}


fun uiTayRotateImages(imageToOrient: Bitmap, degreesToRotate: Int): Bitmap {
    var result: Bitmap = imageToOrient
    try {
        if (degreesToRotate != 0) {
            val matrix = Matrix()
            matrix.setRotate(degreesToRotate.toFloat())
            result = Bitmap.createBitmap(
                imageToOrient,
                0,
                0,
                imageToOrient.width,
                imageToOrient.height,
                matrix,
                true /*filter*/
            )
        }
    } catch (e: java.lang.Exception) {
        Log.d("TAGError", "Exception when trying to orient image", e)

    }
    return result
}

fun String.uiTayBase64toBitmap():Bitmap?{
    val decodedBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

fun Bitmap.bitmapToBase64(): String? {
    val image = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, image)
    val b = image.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun ImageView.uiTayLoadUrl(url:String = UI_TAY_EMPTY, circle : Boolean =false){
    if (url.isNotEmpty()) {
        if (circle) Picasso.get().load(url).transform(CropCircleTransformation()).into(this) else
            Picasso.get().load(url).into(this)
    }
}
