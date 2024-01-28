package com.gb.vale.uitaylibrary.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.utils.UI_TAY_EMPTY
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayBgBorder
import com.gb.vale.uitaylibrary.utils.uiTayBgBorderStroke
import com.gb.vale.uitaylibrary.utils.uiTayGone
import com.gb.vale.uitaylibrary.utils.uiTayHandler
import com.gb.vale.uitaylibrary.utils.uiTayInvisible
import com.gb.vale.uitaylibrary.utils.uiTayVisibility
import com.gb.vale.uitaylibrary.utils.uiTayVisible

class UITayButton @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet?, defaultStyle: Int = 0
) : ConstraintLayout(
    context, attrs, defaultStyle
) {

    private var button = TextView(this.context)
    private var iconStart = ImageView(this.context)
    private var iconEnd = ImageView(this.context)
    private var constraintSet = ConstraintSet()
    private var constraintSetH = ConstraintSet()
    private var neoBtnStyleCurrent = UiTayStyleButton.UI_TAY_PRIMARY
    private var horizontalContentLayout = ConstraintLayout(context)
    private var loading = ProgressBar(this.context)
    private var colorDefaultIcon = true

    var neoBtnBackground : Drawable? = null
        set(value) {
            field = value
            value?.let {
                this.background = it
            }
        }

    var neoBtnTextColor : Int? = null
        set(value) {
            field = value
            value?.let {
                this.button.setTextColor(it)
            }
        }

    var neoBtnText: String = UI_TAY_EMPTY
        set(value) {
            field = value
            button.text = value

        }

    var neoBtnEnable: Boolean = true
        set(value) {
            field = value
            button.isEnabled = value
            this.isEnabled = value
            configStyleInit(value)
        }

    var neoBtnIconColorDefault: Boolean = true
        set(value) {
            field = value
            colorDefaultIcon = value
        }

    var neoBtnIconStart: Drawable? = null
        set(value) {
            field = value
            value?.let {
                iconStart.uiTayVisibility(true)
                iconStart.setImageDrawable(it)
            } ?: iconStart.uiTayVisibility(false)

        }

    var neoBtnIconEnd: Drawable? = null
        set(value) {
            field = value
            value?.let {
                iconEnd.uiTayVisibility(true)
                iconEnd.setImageDrawable(it)
            } ?: iconEnd.uiTayVisibility(false)
        }


    var neoBtnStyle: UiTayStyleButton = UiTayStyleButton.UI_TAY_PRIMARY
        set(value) {
            field = value
            neoBtnStyleCurrent = value
        }

    var neoBtnLoading: Boolean = false
        set(value) {
            field = value
            setLoading(value)
        }

    private fun setLoading(value: Boolean) {
        if (value) {
            loading.uiTayVisible()
            button.uiTayInvisible()
        } else {
            loading.uiTayInvisible()
            button.uiTayVisible()
        }
    }


    init {
        loadAttributes()
        configView()
        constraintSet.clone(horizontalContentLayout)
        constraintSetH.clone(this)
        positionBtn()
    }


    private fun loadAttributes() {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.UITayButton)
        attributeSet.let {
            neoBtnIconColorDefault = it.getBoolean(R.styleable.UITayButton_uiTayBtnIconColorDefault, true)
            neoBtnText = it.getString(R.styleable.UITayButton_uiTayBtnText)
                ?: this.context.resources.getString(R.string.btn_ui_neo_continue)
            neoBtnIconStart = it.getDrawable(R.styleable.UITayButton_uiTayBtnIconStart)
            neoBtnIconEnd = it.getDrawable(R.styleable.UITayButton_uiTayBtnIconEnd)
            neoBtnStyle =
                UiTayStyleButton.values()[it.getInt(R.styleable.UITayButton_uiTayBtnStyle, 0)]
            neoBtnEnable = it.getBoolean(R.styleable.UITayButton_uiTayBtnEnable, true)
        }
        attributeSet.recycle()
    }

    fun setOnClickNeoBtnListener(time: Long = 300, listener: NeoBtnClickListener) {
        this.setOnClickUiTayDelay(time) {
            configStyleSelected()
            uiTayHandler(time) {
                configStyleInit(true)
                listener.onClick(this)
            }
        }
        button.setOnClickUiTayDelay(time) {
            configStyleSelected()
            uiTayHandler {
                configStyleInit(true)
                listener.onClick(this)
            }
        }
    }

    private fun configView() {
        configParent()
        configButton()
        configIconStar()
        configIconEnd()
        configProgress()
    }

    private fun configParent() {
        horizontalContentLayout.id = View.generateViewId()
        val horizontalLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT)
        val layout = LayoutParams(LayoutParams.WRAP_CONTENT, sizeHeight())
        horizontalContentLayout.setPadding(this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_16), 0,
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_16), 0)
        this.layoutParams = layout
        horizontalContentLayout.layoutParams = horizontalLayoutParams
        this.addView(horizontalContentLayout)

    }

    private fun configButton() {
        button.id = View.generateViewId()
        val typeface = ResourcesCompat.getFont(context,R.font.montserrat_bold)
        val layoutParamBtn = LayoutParams(LayoutParams.WRAP_CONTENT
            ,sizeHeight())
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeText().toFloat())
        button.setBackgroundColor(ContextCompat.getColor(this.context, R.color.ui_tay_transparent))
        button.isAllCaps = false
        button.gravity = Gravity.CENTER
        button.typeface = typeface
        button.layoutParams = layoutParamBtn
        loading.uiTayGone()
        horizontalContentLayout.addView(button)
    }

    private fun configIconStar() {
        iconStart.id = View.generateViewId()
        val layoutParamStart = LayoutParams(sizeMax(), sizeMax())
        layoutParamStart.setMargins(0,0,this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_8),0)
        iconStart.layoutParams = layoutParamStart
        horizontalContentLayout.addView(iconStart)

    }

    private fun configIconEnd() {
        iconEnd.id = View.generateViewId()
        val layoutParamEnd = LayoutParams(sizeMax(), sizeMax())
        layoutParamEnd.setMargins(this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_8),0,0,0)
        iconEnd.layoutParams = layoutParamEnd
        horizontalContentLayout.addView(iconEnd)
    }

    private fun configProgress() {
        loading.id = View.generateViewId()
        val layoutLoading = LayoutParams(
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_24),
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_24)
        )
        loading.isIndeterminate = true
        loading.indeterminateTintList =
            ColorStateList.valueOf(this.context.resources.getColor(R.color.ui_tay_btn_loading_color, null))
        loading.layoutParams = layoutLoading
        horizontalContentLayout.addView(loading)
    }


    private fun configStyleInit(value: Boolean) {
        when (neoBtnStyleCurrent) {
            UiTayStyleButton.UI_TAY_PRIMARY -> {
                setEnablePrimary(value)
            }
            else -> {
                setEnableSecondaryLight(value)
            }

        }
    }

    private fun configStyleSelected() {
        when (neoBtnStyleCurrent) {
            UiTayStyleButton.UI_TAY_PRIMARY -> {
                setSelectedPrimary()
            }
            else -> {
                setSelectedSecondary()
            }

        }
    }

    private fun setEnablePrimary(value: Boolean) {
        if (value) this.uiTayBgBorder() else this.uiTayBgBorder(R.color.ui_tay_btn_primary_disable)
        setColorTextAndIcon(text = if (value) R.color.ui_tay_btn_primary_enable else
            R.color.ui_tay_icon_primary_disable, icon = if (value) R.color.ui_tay_btn_primary_enable
        else R.color.ui_tay_icon_primary_disable)
    }
    private fun setSelectedPrimary() {
        this.uiTayBgBorder(R.color.ui_tay_btn_primary_enable_selected)
        setColorTextBtn(R.color.white)
    }

    private fun setEnableSecondaryLight(value: Boolean) {
        if (value) this.uiTayBgBorderStroke() else this.uiTayBgBorderStroke(R.color.ui_tay_btn_secondary_enable_selected)
        setColorTextAndIcon(text =  if (value) R.color.ui_tay_btn_primary_enable else
            R.color.ui_tay_btn_secondary_enable_selected, icon = if (value)
                R.color.ui_tay_btn_primary_enable else R.color.ui_tay_btn_secondary_enable_selected)
    }

    private fun setSelectedSecondary() {
        this.uiTayBgBorderStroke(R.color.ui_tay_btn_primary_enable_selected, R.color.white)
        setColorTextBtn(R.color.ui_tay_btn_secondary_enable_selected)
    }


    private fun setColorTextAndIcon(
        text: Int = R.color.white,
        icon: Int = R.color.white,
    ) {
        if (colorDefaultIcon) iconStart.setColorFilter(
            ContextCompat.getColor(context, icon), android.graphics.PorterDuff.Mode.SRC_IN
        )
        if (colorDefaultIcon)iconEnd.setColorFilter(
            ContextCompat.getColor(context, icon), android.graphics.PorterDuff.Mode.SRC_IN
        )
        setColorTextBtn(text)
    }

    private fun setColorTextBtn( color: Int = R.color.white){
        button.setTextColor(this.context.resources.getColor(color, null))
    }


    private fun sizeHeight() =
        this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_48)

    private fun sizeText() =
        this.context.resources.getDimensionPixelSize(R.dimen.dim_tay_sp_16)

    private fun sizeMax() =
        this.context.resources.getDimensionPixelSize(R.dimen.dim_tay_24)

    private fun positionBtn() {
        constraintSet.connect(button.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(button.id, ConstraintSet.START, iconStart.id, ConstraintSet.END)
        constraintSet.connect(button.id, ConstraintSet.END, iconEnd.id, ConstraintSet.START)
        constraintSet.connect(button.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.applyTo(horizontalContentLayout)
        positionLoading()
    }

    private fun positionIconStar() {
        constraintSet.connect(iconStart.id, ConstraintSet.TOP, button.id, ConstraintSet.TOP)
        constraintSet.connect(iconStart.id, ConstraintSet.END, button.id, ConstraintSet.START)
        constraintSet.connect(iconStart.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(iconStart.id, ConstraintSet.BOTTOM, button.id, ConstraintSet.BOTTOM)
        constraintSet.applyTo(horizontalContentLayout)
        positionIconEnd()
    }
    private fun positionIconEnd() {
        constraintSet.connect(iconEnd.id, ConstraintSet.TOP, button.id, ConstraintSet.TOP)
        constraintSet.connect(iconEnd.id, ConstraintSet.START, button.id, ConstraintSet.END)
        constraintSet.connect(iconEnd.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(iconEnd.id, ConstraintSet.BOTTOM, button.id, ConstraintSet.BOTTOM)
        constraintSet.applyTo(horizontalContentLayout)
        positionHorizontal()
    }

    private fun positionHorizontal() {
        constraintSetH.connect(horizontalContentLayout.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSetH.connect(horizontalContentLayout.id, ConstraintSet.END,ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSetH.connect(horizontalContentLayout.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSetH.connect(horizontalContentLayout.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSetH.applyTo(this)
    }

    private fun positionLoading() {
        constraintSet.connect(loading.id, ConstraintSet.TOP, button.id, ConstraintSet.TOP)
        constraintSet.connect(loading.id, ConstraintSet.END,  button.id, ConstraintSet.END)
        constraintSet.connect(loading.id, ConstraintSet.START,  button.id, ConstraintSet.START)
        constraintSet.connect(loading.id, ConstraintSet.BOTTOM, button.id, ConstraintSet.BOTTOM)
        constraintSet.applyTo(this)
        positionIconStar()

    }
}

fun interface NeoBtnClickListener {
    fun onClick(view: View)
}

enum class UiTayStyleButton(var code: Int) {
    UI_TAY_PRIMARY(0),
    UI_TAY_SECONDARY(1)
}