package com.gb.vale.uitaylibrary.utils

import android.util.Log
import java.io.File


fun String.uiTayDeleteArchive(){
    val delete = File(this)
    if (delete.exists()) {
        if (delete.delete()) {
            Log.v("","file Deleted :$this")
        } else {
            Log.v("","file not Deleted :$this")
        }
    }
}