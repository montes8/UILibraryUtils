package com.gb.vale.uitaylibrary.label.imageurl

import android.text.Layout
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.MotionEvent
import android.widget.TextView
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.gb.vale.uitaylibrary.utils.uiTayTryCatch

typealias UIClickLinkUrl = (String) -> Unit
class UiTayUrlLinkClick : LinkMovementMethod() {

     var uiClickLinkUrl: UIClickLinkUrl = {}
    override fun onTouchEvent(
        widget: TextView,
        buffer: Spannable, event: MotionEvent
    ): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout: Layout = widget.layout
            val line: Int = layout.getLineForVertical(y)
            val off: Int = layout.getOffsetForHorizontal(line, x.toFloat())
            if (buffer.getSpans(off, off, URLSpan::class.java).isNotEmpty()) {
                configLink(action,off,buffer,widget)
                return true
            }
        }
        return super.onTouchEvent(widget, buffer, event)
    }

    private fun configLink(action : Int,off : Int,buffer: Spannable,widget: TextView){
        val link = buffer.getSpans(off, off, URLSpan::class.java)
        if (action == MotionEvent.ACTION_UP) {
            var url = link[0].url.trim { it <= ' ' }
            if (url.startsWith("www")) { url = "http://$url" }
            if (url.startsWith("https://") || url.startsWith("http://") || url.startsWith("tel:") || url.startsWith(
                    "mailto:"
                )) {
                uiTayTryCatch(catch = { widget.context.uiTayShowToast("url no encontrada")}) {
                    uiClickLinkUrl.invoke(url)
                }
            }
        } else {
            Selection.setSelection(buffer,
                buffer.getSpanStart(link[0]),
                buffer.getSpanEnd(link[0])
            )
        }
    }

    companion object {
        private var sInstance: UiTayUrlLinkClick? = null
        val instance: UiTayUrlLinkClick?
            get() {
                if (sInstance == null) sInstance = UiTayUrlLinkClick()
                return sInstance
            }
    }
}