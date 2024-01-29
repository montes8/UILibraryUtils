package com.gb.vale.taylibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout
import com.gb.vale.uitaylibrary.label.UiTayEditLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ctn = findViewById<ConstraintLayout>(R.id.ctnTest)
        val edit = findViewById<UiTayEditLayout>(R.id.editTest)

        edit.setListOptionDropDawn(arrayListOf("item 01","item 02","item 03"))
        edit.setOnListClickTayEditListener(ctn,edit){
            edit.updatePositionSelectedItem(it)
        }

    }
}