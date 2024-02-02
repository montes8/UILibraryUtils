package com.gb.vale.uitaylibrary.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/*fun bitmapDescriptorFromVector(context: Context, vectorResId: Int, color :String): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    val porterDuffColorFilter = PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN)
    vectorDrawable?.colorFilter = porterDuffColorFilter
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


fun uiTaycalculationByDistance( StartP :LatLng,  endP :LatLng) :Triple<Double,Int,Int>{
    val  Radius = 6371;// radius of earth in Km
    val lat1 = StartP.latitude
    val lat2 = endP.latitude
    val lon1 = StartP.longitude
    val lon2 = endP.longitude
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))*
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * asin(sqrt(a))
    val valueResult = Radius * c
    val km = valueResult / 1
    val newFormat =  DecimalFormat("####")
    val kmInDec = Integer.valueOf(newFormat.format(km))
    val meter = valueResult % 1000
    val meterInDec = Integer.valueOf(newFormat.format(meter))
    return Triple(valueResult,kmInDec,meterInDec)
}*/

