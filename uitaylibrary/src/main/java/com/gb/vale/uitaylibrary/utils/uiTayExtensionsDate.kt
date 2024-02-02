package com.gb.vale.uitaylibrary.utils

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.date.UiTayDatePickerSpinner
import com.gb.vale.uitaylibrary.date.UiTayModelDatePicker
import com.gb.vale.uitaylibrary.date.uiTayDatePickerBasic
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun Long.coverterLongToString(format : String = "dd/MM/yyyy"):String{
    return DateFormat.format(format, Date(this)).toString()
}
fun Long.coverterLongToDate():Date{
    return Date(this)
}

@SuppressLint("SimpleDateFormat")
fun String.getFormatDateStrint(formatInit:String = "dd/MM/yy",formatEnd:String = "MMMM yyyy"):String{
    val formatCurrent = SimpleDateFormat(formatInit)
    val dateCurrent = formatCurrent.parse(this)
    val formatUpdate = SimpleDateFormat(formatEnd)
    return dateCurrent?.let { formatUpdate.format(it) }?:""
}



fun String.uiTayStringToCalendar(format : String = "dd/MM/yyy"):Calendar{
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

fun String.uiTayStringToDate(format : String = "dd/MM/yyy"):Date{
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return try {
         sdf.parse(this)?:Date()
    } catch (e: ParseException) {
        e.printStackTrace()
        Date()
    }
}

fun Date.uiTayDateToCalendar(format : String = "dd/MM/yyy"):Calendar{
    val cal = Calendar.getInstance()
    return try {
        cal.time = this
        cal
    } catch (e: ParseException) {
        e.printStackTrace()
        Calendar.getInstance()
    }

}

fun Date.uiTayDateToString(format : String = "dd/MM/yyy"):String{
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.format(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        UI_TAY_EMPTY
    }
}


fun Calendar.uiTayCalendarToString(format : String = "dd/MM/yyy"):String{
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return try {
        sdf.format(this.time)
    } catch (e: ParseException) {
        e.printStackTrace()
        UI_TAY_EMPTY
    }
}

fun Calendar.uiTayCalendarToDate():Date{
    return try {
        this.time
    } catch (e: ParseException) {
       Date()
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



@SuppressLint("SimpleDateFormat")
fun uiTayGetRangeDaysDate(start : String,end : String,format: String = "dd/MM/yyy"):Long {
    try {
        val dateFormat = SimpleDateFormat(format)
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


fun AppCompatActivity.showUiTayDatePickerSpinner(uiModelDP:UiTayModelDatePicker=UiTayModelDatePicker(),
                                             onSuccessDate: ((Pair<Date,String>) -> Unit)? = null){
    val pd = UiTayDatePickerSpinner.newInstance(uiModelDP)
    pd.show(supportFragmentManager, UiTayDatePickerSpinner::class.java.name)
    pd.uiTayClickDatePicker={
        onSuccessDate?.invoke(it)
    }
}


fun AppCompatActivity.showUiTayDatePicker(uiModelDP:UiTayModelDatePicker=UiTayModelDatePicker(),
                                      onSuccessDate: ((Pair<Date,String>) -> Unit)? = null){
    uiTayDatePickerBasic(uiModelDP) {onSuccessDate?.invoke(it)}
}


fun Context.showUiTayHour(actionTime: ((String) -> Unit)? = null){
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minutes = c.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        this, R.style.UITayTimePickerBasic,
        { _, hourOfDay, minute ->
            actionTime?.invoke("$hourOfDay:$minute:00")
        },
        hour,
        minutes,
        false
    )

    timePickerDialog.show()
}


fun Context.showUiTayHourSpinner(actionTime: ((String) -> Unit)? = null){
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minutes = c.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        this, R.style.UITayTimePickerSpinner,
        { _, hourOfDay, minute ->
            actionTime?.invoke("$hourOfDay:$minute:00")
        },
        hour,
        minutes,
        false
    )

    timePickerDialog.show()
}
