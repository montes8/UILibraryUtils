package com.gb.vale.uitaylibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.manager.style.CustomTypefaceSpan
import java.util.regex.Pattern

fun TextView.uiTaySetColouredSpanClick(
    word: String,
    color: Int = ContextCompat.getColor(this.context,R.color.tay_color_general),
    isUnderLine: Boolean = true,
    isBold: Boolean = false,
    block: () -> Unit
) {
    movementMethod = LinkMovementMethod.getInstance()
    val fontBold = ResourcesCompat.getFont(this.context, R.font.montserrat_bold)
    val spannableString = SpannableString(text)
    val start = text.indexOf(word)
    val end = text.indexOf(word) + word.length
    try {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                block()
            }

            @SuppressLint("ResourceAsColor")
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = color
                ds.isUnderlineText = isUnderLine
            }
        }
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        if (isBold)spannableString.setSpan(fontBold?.let { CustomTypefaceSpan(it) }, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        text = spannableString
    } catch (e: IndexOutOfBoundsException) {
        println("'$word' was not not found in TextView text")
    }

}

fun TextView.uiTaySetSpanCustom(
    text: String,
    word: String
) {
    this.text = this.context.uiTaySetSpanCustom(text,word)
}

fun Context.uiTaySetSpanCustom(
    text: String,
    word: String
):SpannableString {
    val fontBold = ResourcesCompat.getFont(this, R.font.montserrat_bold)
    val spannableString = SpannableString(text)
    val start = text.indexOf(word)
    val end = text.indexOf(word) + word.length
    return try {
        spannableString.setSpan(fontBold?.let { CustomTypefaceSpan(it) }, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableString
    } catch (e: IndexOutOfBoundsException) {
        SpannableString(UI_TAY_EMPTY)
    }
}

fun String.validateEmail() : Boolean{
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(this)
    return matcher.find()
}

fun String.validatePhoneFormat() : Boolean{
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