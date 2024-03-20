package com.gb.vale.uitaylibrary.swipe

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.gb.vale.uitaylibrary.R

class UiTayCardSwipeButton(
    private val context: Context,
    private val color: Int = context.getColor(R.color.ui_tay_color_swipe),
    private val imageResId: Int = 0,
    private val text: String = "",
    private val textSize: Float = 0f,
    private val listener: CardSwipeButtonListener
) {

    private var pos: Int = 0
    private var clickRegion: RectF? = null

    fun onClick(x: Float, y: Float): Boolean {
        if (clickRegion != null && clickRegion?.contains(x, y) == true) {
            listener.onClick(pos)
            return true
        }
        return false
    }

    fun onDraw(c: Canvas, rectF: RectF, pos: Int) {
        val p = Paint()
        p.color = color
        c.drawRect(rectF, p)
        if (imageResId != 0) {
            drawIcon(c, rectF, p)
        } else
            drawText(c, rectF)
        clickRegion = rectF
        this.pos = pos
    }

    private fun drawIcon(c: Canvas, rectF: RectF, p: Paint) {
        val deleteIcon = context.getDrawable(imageResId)
        val bitmap = drawableToBitmap(deleteIcon)
        c.drawBitmap(bitmap, (rectF.left + rectF.right) / 2, (rectF.top + rectF.bottom) / 2, p)
    }

    private fun drawText(c: Canvas, rectF: RectF) {
        val p = Paint()
        val r = Rect()
        val cHeight = rectF.height()
        val cWidth = rectF.width()
        p.color = Color.WHITE
        p.textSize = textSize
        p.textAlign = Paint.Align.LEFT
        p.isAntiAlias = true
        p.getTextBounds(text, 0, text.length, r)
        val y = cHeight / 2f + r.height() / 2f - r.bottom.toFloat()
        val x = cWidth / 2f - r.width() / 2f - r.left.toFloat()
        c.drawText(text, rectF.left + x, rectF.top + y, p)
    }

    private fun drawableToBitmap(d: Drawable?): Bitmap {
        if (d is BitmapDrawable) return d.bitmap
        val bitmap =
            Bitmap.createBitmap(
                d?.intrinsicWidth ?: 0,
                d?.intrinsicHeight ?: 0,
                Bitmap.Config.ARGB_8888
            )
        val canvas = Canvas(bitmap)
        d?.setBounds(0, 0, canvas.width, canvas.height)
        d?.draw(canvas)
        return bitmap
    }

}

fun interface CardSwipeButtonListener {
    fun onClick(pos: Int)
}