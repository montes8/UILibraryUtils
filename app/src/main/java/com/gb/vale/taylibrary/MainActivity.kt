package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.animation.fadeOneToZero
import com.gb.vale.uitaylibrary.extra.UiTayRatingBar
import com.gb.vale.uitaylibrary.label.UiTayOtpCode


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<UiTayRatingBar>(R.id.btnTest)
        btn.onRatingBarSelectedListener={
            Log.d("ratinu",it.toString())
        }

    }
}