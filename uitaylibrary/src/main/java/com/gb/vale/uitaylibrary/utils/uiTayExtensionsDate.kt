package com.gb.vale.uitaylibrary.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.view.View
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.date.UiTayDatePickerDialog
import com.gb.vale.uitaylibrary.date.UiTayModelDatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.pow


@SuppressLint("SimpleDateFormat")
fun Date.uiTayFormatString(format : String = "dd/MM/yyy"):String{
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.format(this)

}

fun String.uiTayFormatCalendar(format : String = "dd/MM/yyy"):Calendar{
    val cal = Calendar.getInstance()
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return try {
        sdf.parse(this)?.let { cal.time = it }
        cal
    } catch (e: ParseException) {
        e.printStackTrace()
        Calendar.getInstance()
    }

}

@SuppressLint("SimpleDateFormat")
fun uiTayGetRangeDaysDate(start : String,end : String,format: String = "dd/MM/yyy"):Long {
    try {
        val dateFormat = SimpleDateFormat("dd/MM/yyy")
        var dateInit: Date? = null
        var dateEnd: Date? = null
        dateFormat.parse(start)?.let { dateInit = it}
        dateFormat.parse(end)?.let { dateEnd = it }
        dateInit?.let {init->
            dateEnd?.let { end->
                return ((end.time - init.time) / 86400000)
            }
        }
        return  0
    }catch (e:Exception){
        return  0
    }
}

fun uiTayGetInitEndMonths(calendar : Calendar?=null,formatCurrent: String = "dd/MM/yyyy"): Pair<String,String> {
    val calendarInit = calendar?:Calendar.getInstance()
    val calendarEnd = calendar?:Calendar.getInstance()
    val format = SimpleDateFormat(formatCurrent, Locale.getDefault())
    val dateMax = calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH)
    calendarEnd.set(Calendar.DAY_OF_MONTH,dateMax)
    calendarInit.add(Calendar.DATE, 1)
    val dateStar = format.format(calendarInit.time)
    val dateEnd = format.format(calendarEnd.time)
    return Pair(dateStar,dateEnd)
}


fun uiTayGetListMonthsFilter(range:Int = 3,formatCurrent : String = "MMMM yyyy"): List<String> {
    val calendar = Calendar.getInstance()
    val format = SimpleDateFormat(formatCurrent, Locale.getDefault())
    val monthsName = mutableListOf<String>()
    for (i in 0 until range) {
        val monthName = format.format(calendar.time)
        monthsName.add(monthName)
        calendar.add(Calendar.MONTH, -1)
    }
    return monthsName
}

@SuppressLint("SimpleDateFormat")
fun String.getFormatDateStrint(formatInit:String = "dd/MM/yy",formatEnd:String = "MMMM yyyy"):String{
    val formatCurrent = SimpleDateFormat(formatInit)
    val dateCurrent = formatCurrent.parse(this)
    val formatUpdate = SimpleDateFormat(formatEnd)
    return dateCurrent?.let { formatUpdate.format(it) }?:""
}


@SuppressLint("SimpleDateFormat")
fun String.uiTayFormatTwelveHour():String{
    return try {
        val formatHour = SimpleDateFormat("hh:mm:ss")
        val hourCurrent: Date = formatHour.parse(this) as Date
        val converter = SimpleDateFormat("hh:mm a")
        converter.format(hourCurrent)
    } catch (e: ParseException) {
        ""
    }
}


fun AppCompatActivity.showUiTayDatePicker(onSuccessDate: ((Pair<Date,String>) -> Unit)? = null){
    val pd = UiTayDatePickerDialog.newInstance(
    )
    pd.show(supportFragmentManager, UiTayDatePickerDialog::class.java.name)
    pd.uiTayClickDatePicker={
        onSuccessDate?.invoke(it)
    }
}
