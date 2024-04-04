package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gb.vale.uitaylibrary.label.UiTayEditBasic
import com.gb.vale.uitaylibrary.list.model.UiTayModelCustom


class MainActivity : AppCompatActivity() {



    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ctn = findViewById<ConstraintLayout>(R.id.crnaa)
        val text = findViewById<UiTayEditBasic>(R.id.listtest)
        text.setListOptionCustomDropDawn(
            arrayListOf(
                UiTayModelCustom(title ="dfghjkl", subTitle = "dfghjk", message = "fvgbhjklkjhg"),
                UiTayModelCustom(title ="dfghjkl", subTitle = "dfghjk", message = "fvgbhjklkjhg"),
                UiTayModelCustom(title ="dfghjkl", subTitle = "dfghjk", message = "fvgbhjklkjhg")) )

        text.setOnListClickTayEditListener(ctn,text){

        }

    }



}