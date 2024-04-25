package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {



    var lista = arrayListOf("sddd","srr","srrrrr","sht","rtt","yund","zzzz","xxxx")

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       /* val ctn = findViewById<ConstraintLayout>(R.id.crnaa)
        val imgLogo = findViewById<UiTayEditBasic>(R.id.imgLogo)

        imgLogo.setOnIconClickTayEditListener{
            uiTayShowToast("click")
        }*/
    }


    }



