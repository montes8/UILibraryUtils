package com.gb.vale.uitaylibrary.manager.screencapture

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/*class MediaProjectController(private val context: Activity, private val listener: MediaProjectControllerListener?) {

    private lateinit var mediaProjectionManager: MediaProjectionManager
    private val cod = 1222

    companion object {
        private const val REQUEST_CAPTURE = 1
        var projection: MediaProjection? = null

    }

    fun initialService(){
        if (!isPermissionGiven()) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context.packageName}")
            )
            context.startActivityForResult(intent, cod)
        } else {
            starCapture()
        }
    }

    private fun starCapture(){
        val intent = Intent(context, FloatingWidgetService::class.java).setAction(FloatingWidgetService.ACTION_ENABLE_CAPTURE)
        ContextCompat.startForegroundService(context, intent)
        mediaProjectionManager = context.getSystemService(Service.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        context.startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_CAPTURE)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == cod) {
            if (isPermissionGiven()){
                starCapture()
            } else{
                listener?.onPermissionDenied()
            }

        }
        if (requestCode == REQUEST_CAPTURE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                try {
                    projection = data?.let { mediaProjectionManager.getMediaProjection(resultCode, it) }
                    listener?.onSuccess()
                } catch (e: Exception) {
                    Log.e("ERROR_MEDIA_PROJECTION", e.message ?: String())
                }
            } else {
                projection = null
                listener?.onPermissionDeniedCapture()
            }
        }
    }



    @SuppressLint("ObsoleteSdkInt")
    private fun isPermissionGiven(): Boolean {
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context))
    }

    interface MediaProjectControllerListener {
        fun onPermissionDenied()
        fun onPermissionDeniedCapture()
        fun onSuccess()
    }
}*/
