package com.gb.vale.uitaylibrary.extra

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.utils.UI_TAY_EMPTY
import com.gb.vale.uitaylibrary.utils.uiTayBgBorderStroke
import com.gb.vale.uitaylibrary.utils.uiTayGone
import com.gb.vale.uitaylibrary.utils.uiTayHandler
import com.gb.vale.uitaylibrary.utils.uiTayVisible

class UITaySnackBar @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet?, defaultStyle: Int = 0
) : ConstraintLayout(
    context, attrs, defaultStyle
) {

    private var textSBar = TextView(this.context)
    private var constraintSet = ConstraintSet()
    private var uiTayDuration = 2000L
    private var uiTayText = UI_TAY_EMPTY

    var uiTaySBText: String = UI_TAY_EMPTY
        set(value) {
            field = value
            uiTayText = value
            textSBar.text = value
        }

    var uiTaySBDuration: Int = 2000
        set(value) {
            field = value
            uiTayDuration = (value * 1000).toLong()
        }

    init {
        loadAttributes()
        configView()
        constraintSet.clone(this)
        positionView()
        this.uiTayGone()
    }

    private fun configView() {
        textSBar.id = View.generateViewId()
        val typeface = ResourcesCompat.getFont(context, R.font.montserrat_medium)
        val layoutText = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        textSBar.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            this.context.resources.getDimensionPixelSize(R.dimen.dim_tay_snack_sp_text).toFloat()
        )
        textSBar.setTextColor(ContextCompat.getColor(this.context,R.color.tay_snack_bar_text))
        textSBar.typeface = typeface
        this.uiTayBgBorderStroke(R.color.tay_snack_bar_bg_stroke,
            R.color.tay_snack_bar_bg_stroke ,radius = R.dimen.dim_tay_snack_bar_radius)
        this.setPadding(getUiTayPadding(),getUiTayPadding(),getUiTayPadding(),getUiTayPadding())
        textSBar.layoutParams = layoutText
        this.addView(textSBar)
    }

    private fun getUiTayPadding() =  this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_16)

    private fun loadAttributes() {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.UITaySnackBar)
        attributeSet.let {
            uiTaySBText =
                it.getString(R.styleable.UITaySnackBar_uiTaySBText)
                    ?: context.getString(R.string.tay_ui_script)

            uiTaySBDuration = it.getInt(R.styleable.UITaySnackBar_uiTaySBDuration, 2)

        }
        attributeSet.recycle()
    }

    private fun positionView() {
        constraintSet.connect(
            textSBar.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            textSBar.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            textSBar.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            textSBar.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(this)
    }

    fun showNeoSB(text : String = uiTayText) {
        this.uiTayVisible()
        textSBar.text = text
        uiTayHandler(uiTayDuration) { this.uiTayGone() }
    }

}