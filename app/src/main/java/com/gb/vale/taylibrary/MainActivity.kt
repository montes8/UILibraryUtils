package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.utils.uiTayBgGradient

class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var viewColor = findViewById<View>(R.id.viewColor)
        viewColor.uiTayBgGradient()
    }

}



