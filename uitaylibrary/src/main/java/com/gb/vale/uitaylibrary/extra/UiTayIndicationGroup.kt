package com.gb.vale.uitaylibrary.extra

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.utils.uiTayBgBorderCircle
import com.gb.vale.uitaylibrary.utils.uiTayBgBorderStroke

class UiTayIndicationGroup(context: Context?, attrs: AttributeSet?) : RadioGroup(context, attrs) {
    private val layout = LinearLayout.LayoutParams(
        this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_indication_size_unselected),
        this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_indication_size_unselected)
    )
    fun setChecked(position: Int) {
        this.changeColorButtons()
        val layoutS = LinearLayout.LayoutParams(
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_indication_width_selected),
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_indication_height_selected)
        )
        layoutS.setMargins(this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_4),0,
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_4),0)
        this.getChildAt(position).uiTayBgBorderStroke(R.color.tay_indication_stroke_selected,
            R.color.tay_indication_solid_selected,
            R.dimen.dim_tay_indication_radius_selected)
        this.getChildAt(position).layoutParams = layoutS
        this.check(position)
    }

    private fun changeColorButtons() {
        val countButtons = childCount
        layout.setMargins(this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_4),0,
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_4),0)
        for (i in 0 until countButtons) {
            getChildAt(i).uiTayBgBorderCircle(R.color.tay_indication_stroke_unselected,
                R.color.tay_indication_stroke_unselected)
            getChildAt(i).layoutParams = layout
        }
    }


}