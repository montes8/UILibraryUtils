package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.utils.uiTayIsDeviceLocked

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       Log.d("uiTayIsDeviceLocked",uiTayIsDeviceLocked(this).toString())


    }
}