package com.gb.vale.taylibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.label.UiTayTextHtml
import com.gb.vale.uitaylibrary.utils.uiTayIsDeviceLocked
import com.gb.vale.uitaylibrary.utils.uiTayShowToast

class MainActivity : AppCompatActivity() {

    val url = "<span style=\"font-weight:700\">Pyrin (PYI) </span><img src=\"https://www.unmineable.com/img/logos/PYI.png?v6\" style=\"height: 17px;width:17px;vertical-align: middle;\" alt=\"PYI icon\"> is now <a href=\"https://unmineable.com/coins/PYI\">available for mining</a>! start earning PYI not just with the <span style=\"font-weight:700\">PyrinHash</span> algorithm but with any of our <span style=\"font-weight:700\">CPU, Graphics Card or ASIC</span> compatible algorithms. Happy mining!"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<UiTayTextHtml>(R.id.tayTextHtml)
        text.uiTayTextHtml = url
        text.setOnTayTextHlmClickLink{
            uiTayShowToast(it)
        }
       Log.d("uiTayIsDeviceLocked",uiTayIsDeviceLocked(this).toString())


    }
}