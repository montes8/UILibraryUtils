package com.gb.vale.uitaylibrary.list

import android.view.ContextThemeWrapper
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.list.adapter.UiTayListAdapter
import com.gb.vale.uitaylibrary.utils.uiTayVisibility
import androidx.recyclerview.widget.LinearLayoutManager
import com.gb.vale.uitaylibrary.utils.uiTayBgBorderStroke
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams

fun ConstraintLayout.uiTayListSpinner(
    viewTop: View,
    list: List<String> = ArrayList(), position: Int = -1, positionBottom: Boolean = true,
    onClickContent: () -> Unit, onClickSelected: (Int) -> Unit
): LinearLayout {
    val linear = LinearLayout(this.context)
    if (list.isNotEmpty()) {
        val adapter = UiTayListAdapter()
        val rvList = RecyclerView(ContextThemeWrapper(context, R.style.UITayStyleList))


        rvList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        rvList.id = View.generateViewId()
        linear.id = View.generateViewId()
        linear.elevation = 12f
        if (list.size < 4) rvList.isVerticalScrollBarEnabled = true
        val paramLinear = LayoutParams(
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_0),
            LayoutParams.WRAP_CONTENT
        )
        linear.uiTayBgBorderStroke(
            R.color.tay_edit_list_bg_content_stroke,
            R.color.tay_edit_list_bg_content_solid,
            R.dimen.dim_tay_bg_edit_basic_list_radius
        )
        paramLinear.setMargins(
            0, if (positionBottom) this.resources.getDimensionPixelSize(R.dimen.dim_tay_12) else 0,
            0,
            if (!positionBottom) this.resources.getDimensionPixelSize(R.dimen.dim_tay_12) else 0,
        )
        linear.setPadding(
            0,
            this.resources.getDimensionPixelSize(R.dimen.dim_tay_16),
            0,
            this.resources.getDimensionPixelSize(R.dimen.dim_tay_16)
        )
        val paramRv = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        paramLinear.matchConstraintMaxHeight =
            this.context.resources.getDimensionPixelOffset(R.dimen.dim_tay_bg_edit_basic_list_max_height)
        rvList.layoutParams = paramRv
        linear.layoutParams = paramLinear
        rvList.setPadding(0, 0, 0, 0)
        this.addView(linear)
        linear.addView(rvList)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        rvList.adapter = adapter
        adapter.selectedPosition(position)
        adapter.list = list
        this.setOnClickListener {
            onClickContent.invoke()
            rvList.uiTayVisibility(false)
            this.removeView(linear)
        }
        adapter.onClickOption = {
            onClickSelected.invoke(it)
            rvList.uiTayVisibility(false)
            this.removeView(linear)
        }

        if (positionBottom) {
            constraintSet.connect(linear.id, ConstraintSet.TOP, viewTop.id, ConstraintSet.BOTTOM)
        } else {
            constraintSet.connect(linear.id, ConstraintSet.BOTTOM, viewTop.id, ConstraintSet.TOP)
        }
        constraintSet.connect(linear.id, ConstraintSet.START, viewTop.id, ConstraintSet.START)
        constraintSet.connect(linear.id, ConstraintSet.END, viewTop.id, ConstraintSet.END)
        constraintSet.applyTo(this)
    }

    return linear
}