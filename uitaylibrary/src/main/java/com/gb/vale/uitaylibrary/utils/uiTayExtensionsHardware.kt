package com.gb.vale.uitaylibrary.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log


fun Context.uiTayViewDialedNumber(number : String = UI_TAY_EMPTY,key : String = "tel:",uiTayAction : String =
    Intent.ACTION_DIAL
){
    val uri = Uri.parse("$key$number")
    val intent = Intent(uiTayAction, uri)
    try {
        this.startActivity(intent)
    } catch (e: SecurityException) {
        Log.e(UI_TAY_TAG_ERROR,e.message.toString())
    }
}

fun Context.uiTayDialedNumber(number : String = UI_TAY_EMPTY,key : String = "tel:",
    uiTayAction : String = Intent.ACTION_DIAL){
    try {
        val intent = Intent(uiTayAction)
        intent.setData(Uri.parse("$key$number"))
        this.startActivity(intent)
    } catch (e: SecurityException) {
        Log.e(UI_TAY_TAG_ERROR,e.message.toString())
    }
}
