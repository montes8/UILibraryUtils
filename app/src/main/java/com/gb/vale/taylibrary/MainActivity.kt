package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.utils.uiTayGenerateQrImage


class MainActivity : AppCompatActivity() {



    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<ImageView>(R.id.btntest)
        text.setImageBitmap("sdfghjkloiuytfvbnm".uiTayGenerateQrImage())

    }



}