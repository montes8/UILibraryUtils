package com.gb.vale.uitaylibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.gb.vale.uitaylibrary.R
import java.text.NumberFormat
import java.util.regex.Pattern


fun String.uiTayValidateEmail() : Boolean{
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(this)
    return matcher.find()
}

fun String.uiTayValidatePhoneFormat() : Boolean{
    return if (this.isNotEmpty()){
        val numberStart = this[0]
        if (numberStart == '9') {
            if (this.contains('*')) {
                false
            } else {
                val phoneRegex = "(?=.[0-9]).{9}"
                val pattern = Pattern.compile(phoneRegex)
                pattern.matcher(this).matches()
            }
        } else
            false
    }else{
        false
    }
}

fun String.uiTayFormatDecimal(): String {
    return try {
        val formatParse = NumberFormat.getInstance()
        formatParse.maximumFractionDigits = 2
        formatParse.minimumFractionDigits = 2
        formatParse.format(this.toDouble())

    } catch (e: Exception) {
        "0.00"
    }
}

@SuppressLint("SetTextI18n")
fun TextView.uiTaySplitTextTwo(text: String) {
    try {
        val parts = text.split(" ").toTypedArray()
        val part1 = parts[0]
        val part2 = parts[1]
        this.text = "${part1.substring(0,1)}${part2.substring(0,1)}"
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String.uiTayValidateCap():Boolean{
    val capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var count = 0
    capitalLetters.forEach {
        this.forEach {pass->
            if (pass == it) count += 1
        }
    }
    return count >= 1
}

fun String.uiTayValidateLow():Boolean{
    val lowerCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".lowercase()
    var count = 0
    lowerCase.forEach {
        this.forEach {pass->
            if (pass == it) count += 1
        }
    }
    return count >= 1
}

fun String.uiTayValidateNumber():Boolean{
    val numbers = "0123456789"
    var count = 0
    numbers.forEach {
        this.forEach {pass->
            if (pass == it) count += 1
        }
    }
    return count >= 1
}


fun EditText.uiTayValidateCharacterLettersNumbers(){
    val letterFilter = InputFilter { source, start, end, _, _, _ ->
        var filtered = ""
        for (i in start until end) {
            val character = source[i]
            if (!Character.isWhitespace(character) && Character.isLetter(character) || Character.isDigit(character)) {
                filtered += character
            }
        }

        filtered
    }

    this.filters = arrayOf(letterFilter)
}


fun String.uiTayRemoveSpaces(): String = replace("\\s".toRegex(), "")






