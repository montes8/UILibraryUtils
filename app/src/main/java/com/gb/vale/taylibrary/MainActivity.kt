package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.gb.vale.uitaylibrary.utils.uiTayLoadUrl


class MainActivity : AppCompatActivity() {



    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgLogo = findViewById<AppCompatImageView>(R.id.imgLogo)
        imgLogo.uiTayLoadUrl(url =  "https://www.cartoonbucket.com/wp-content/uploads/2015/05/Stylish-Pikachu-Image.jpg",circle = true)
        }

    }



