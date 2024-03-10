package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.manager.camera.UiTayCameraManager
import com.gb.vale.uitaylibrary.utils.uiTayIsDeviceLocked
import com.gb.vale.uitaylibrary.utils.uiTayWriteCodeQrImage


class MainActivity : AppCompatActivity(),UiTayCameraManager.CameraControllerListener {


    private var cameraManager : UiTayCameraManager? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraManager = UiTayCameraManager(this,"qrscan",this)
        val text = findViewById<Button>(R.id.btntest)
        text.setOnClickListener{
           cameraManager?.doCamera()
        }
       Log.d("uiTayIsDeviceLocked",uiTayIsDeviceLocked(this).toString())


    }

    override fun onCameraPermissionDenied() {

    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
       Log.d("qrImageurl","${img.uiTayWriteCodeQrImage()}")
    }
}