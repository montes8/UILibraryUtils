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
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.gb.vale.uitaylibrary.R
import java.text.NumberFormat
import kotlin.math.pow
import kotlin.math.sqrt


fun View.uiTayVisible(){
    this.visibility = View.VISIBLE
}

fun View.uiTayGone(){
    this.visibility = View.GONE
}

fun View.uiTayInvisible(){
    this.visibility = View.INVISIBLE
}
fun View.uiTayVisibility(value:Boolean){
   if (value)this.uiTayVisible() else this.uiTayGone()
}

fun View.uiTayInVisibility(value:Boolean){
    if (value)this.uiTayVisible() else this.uiTayInvisible()
}


fun uiTayHandler(
    time: Long = 200,
    func: (() -> Unit)? = null
) {
    Handler(Looper.getMainLooper()).postDelayed({
        func?.invoke()
    }, time)
}

fun uiTayTryCatch( func: (() -> Unit)? = null){
    try {  func?.invoke() }catch (e: Exception){e.printStackTrace()}
}
fun View.setOnClickUiTayDelay(time: Long = 700, onClick: (View) -> Unit) {
    this.setOnClickListener {
        it.isEnabled = false
        uiTayHandler(time) { it.isEnabled = true }
        onClick(it)
    }
}

fun Int.toPxUiTay(context: Context): Int {
    val metrics = context.resources.displayMetrics
    val densityFactor = metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT
    return (this * densityFactor).toInt()
}



fun View.uiTayBgBorder(color : Int =
                            R.color.tay_color_general, radius : Int){
    this.background = this.context.uiTayDrawableRound(color,radius)
}

fun Context.uiTayDrawableRound(color : Int =
                              R.color.tay_color_general, radius : Int):Drawable{
    val shape = GradientDrawable()
    shape.setColor(ContextCompat.getColor(this,color))
    shape.cornerRadius = this.resources.getDimension(radius)
    return  shape
}

fun View.uiTayBgBorderCircle(color : Int =
                            R.color.tay_color_general, colorStroke : Int =
                            0,withStroke : Int = 2){
    this.background = this.context.uiTayDrawableCircle(color,colorStroke,withStroke)
}

fun View.uiTayBgCircleGradient(colorTop : Int = R.color.tay_color_general,
                          colorBottom : Int = R.color.tay_color_general){
    this.background = this.context.uiTayDrawableCircleGradient(colorTop,colorBottom)
}

fun Context.uiTayDrawableCircle(color : Int = R.color.tay_color_general,colorStroke : Int =
    0,withStroke : Int = 2):Drawable{
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.OVAL
    shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    shape.setColor(this.getColor(color))
    if (colorStroke != 0) shape.setStroke(withStroke, this.getColor(colorStroke))
    return shape
}

fun Context.uiTayDrawableCircleGradient(colorTop : Int = R.color.tay_color_general,
                                   colorBottom : Int = R.color.tay_color_general):Drawable{
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.OVAL
    shape.colors = intArrayOf(
        ContextCompat.getColor(this,colorTop),
        ContextCompat.getColor(this,colorBottom),
    )
    shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    return shape
}

fun Context.uiTayDrawableStroke(colorStroke : Int = R.color.tay_color_general,colorSolid : Int = R.color.ui_tay_white,
                           radius : Int, withStroke : Int = 2):Drawable{
    val shape = GradientDrawable()
    shape.setColor(ContextCompat.getColor(this,colorSolid))
    shape.setStroke(withStroke,ContextCompat.getColor(this,colorStroke))
    shape.cornerRadius = this.resources.getDimension(radius)
    return shape
}


fun View.uiTayBgBorderStroke(colorStroke : Int = R.color.tay_color_general,colorSolid : Int = R.color.ui_tay_white,
                        radius : Int, withStroke : Int = 2){
    this.background = this.context.uiTayDrawableStroke(colorStroke,colorSolid,radius,withStroke)
}

fun uiTayFullScreen(window : Window){
    window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

fun View.uiTayBgRadiusCustom(color : Int =
                            R.color.ui_tay_white, radiusTop : Int,
                        radiusButton : Int){
    this.background = this.context.uiTayDrawableRadius(color,radiusTop,radiusButton)
}

fun Context.uiTayDrawableRadius(color : Int =
                               R.color.ui_tay_white, radiusTop : Int,
                           radiusButton : Int): Drawable {
    val shape = GradientDrawable()
    shape.setColor(ContextCompat.getColor(this,color))
    shape.cornerRadii = floatArrayOf(
        converterDimen(radiusTop), converterDimen(radiusTop), converterDimen(radiusTop),
        converterDimen(radiusTop), converterDimen(radiusButton),  converterDimen(radiusButton),
        converterDimen(radiusButton),  converterDimen(radiusButton))
    return shape
}

fun Context.converterDimen(value : Int) = this.resources.getDimension(value)

fun Context.uiTayShowToast(value : String){
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}

fun Context.uiTaycopy(text : String,message:String = getString(R.string.tay_ui_text_message_copy)){
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
    this.uiTayShowToast(message)
}

fun Context.uiTayOpenUrl(url : String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    this.startActivity(intent)
}


fun uiTayPixelsToSp(context: Context,px: Float) = px / context.resources.displayMetrics.scaledDensity

@SuppressLint("MissingPermission")
fun Context?.uiTayIsConnected(): Boolean {
    return this?.let {
        val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getNetworkCapabilities(cm.activeNetwork)
            ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
    } ?: false
}

fun Context?.uiTayIsAirplaneModeActive(): Boolean {
    return this?.let {
        return Settings.Global.getInt(it.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } ?: false

}


fun Activity.uiTayCheckIsTablet(): Boolean {

    val metrics = DisplayMetrics()

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = this.display
        display?.getRealMetrics(metrics)
    } else {
        @Suppress("DEPRECATION")
        val display = this.windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(metrics)
    }

    var isTablet = false
    val widthInches: Float = metrics.widthPixels / metrics.xdpi
    val heightInches: Float = metrics.heightPixels / metrics.ydpi
    val diagonalInches =
        sqrt(
            widthInches.toDouble().pow(2.0) + heightInches.toDouble().pow(2.0)
        )
    if (diagonalInches >= 7.0) {
        isTablet = true
    }

    return isTablet
}

fun Activity.uiTayHideKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}


fun String.formatDecimal(): String {
    return try {
        val formatParse = NumberFormat.getInstance()
        formatParse.maximumFractionDigits = 2
        formatParse.minimumFractionDigits = 2
        formatParse.format(this.toDouble())

    } catch (e: Exception) {
        "0.00"
    }
}


