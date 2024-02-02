package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.animation.fadeOneToZero
import com.gb.vale.uitaylibrary.animation.fadeZeroToOne
import com.gb.vale.uitaylibrary.animation.uiTayDoBounce
import com.gb.vale.uitaylibrary.animation.uiTayDoBounceAnimation
import com.gb.vale.uitaylibrary.utils.ModelTest
import com.gb.vale.uitaylibrary.utils.uiTayJsonToObjet
import com.gb.vale.uitaylibrary.utils.uiTayObjetToJson


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<View>(R.id.btnTest)
        btn.fadeOneToZero()
        btn.setOnClickListener {

        }

    }
}