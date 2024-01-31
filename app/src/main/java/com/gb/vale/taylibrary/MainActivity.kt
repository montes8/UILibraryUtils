package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.utils.showUiTayDatePicker


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<TextView>(R.id.btnTest)
        btn.setOnClickListener {
            showUiTayDatePicker(){
                Log.d("daaa",it.toString())
            }

        }
    }
}