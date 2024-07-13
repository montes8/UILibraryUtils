package com.gb.vale.uitaylibrary.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telecom.Call
import android.util.Log


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

fun Context.uiTayViewCall(){
    try {
        val intent = Intent()
        intent.setClass(this, Call::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    } catch (e: SecurityException) {
        Log.e(UI_TAY_TAG_ERROR,e.message.toString())
    }
}
