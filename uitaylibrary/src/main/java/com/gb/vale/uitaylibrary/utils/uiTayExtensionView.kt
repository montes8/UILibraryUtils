package com.gb.vale.uitaylibrary.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.dialog.UiTayDialog
import com.gb.vale.uitaylibrary.dialog.UiTayDialogLayout
import com.gb.vale.uitaylibrary.dialog.UiTayDialogLayoutBlock
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.extra.UiTayIndicationGroup
import com.gb.vale.uitaylibrary.swipe.UiTayCardSwipeButton
import com.gb.vale.uitaylibrary.swipe.UiTayCardSwipeHelper

fun AppCompatActivity.showUiTayDialogLayout(
    layout: Int,
    cancelable: Boolean = true,
    func: UiTayDialogLayoutBlock
) {
    val dialog = UiTayDialogLayout(layout, func)
    dialog.dialog?.setCancelable(cancelable)
    dialog.isCancelable = cancelable
    dialog.show(this.supportFragmentManager, UiTayDialogLayout::class.java.name)
}


fun AppCompatActivity.showUiTayDialog(model : UiTayDialogModel = UiTayDialogModel(),
    func: ((action: Boolean) -> Unit)? = null,) {
    val dialog = UiTayDialog.newInstance(model)
    dialog.dialog?.setCancelable(model.isCancel)
    dialog.isCancelable = model.isCancel
    dialog.func = { func?.invoke(it) }
    dialog.show(this.supportFragmentManager, UiTayDialog::class.java.name)
}

fun Context.uiTayDialogList(
    list: ArrayList<String> = ArrayList(), titleVisibility : Boolean = true,
    actionClick: ((action: Int) -> Unit)? = null
) {
    val builderSingle = AlertDialog.Builder(this)
    if (titleVisibility)builderSingle.setTitle(getString(R.string.ui_tay_title_dialog_list))
    val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
    builderSingle.setAdapter(arrayAdapter) { dialog, which ->
        actionClick?.invoke(which)
        dialog.dismiss()
    }
    builderSingle.show()
}

fun RecyclerView.uiTayAddSwipe(
    context: Context,
    buttonWidth: Int = 300, marginStart: Int = 0,
    addSwipeButtons: (ArrayList<UiTayCardSwipeButton>) -> Unit
) {
    object : UiTayCardSwipeHelper(context, this, buttonWidth,marginStart) {
        override fun instanceCardSwipe(
            viewHolder: RecyclerView.ViewHolder,
            buffer: ArrayList<UiTayCardSwipeButton>
        ) {
            addSwipeButtons(buffer)
        }
    }
}


fun Context.uiTayAddRadioButton(radio : UiTayIndicationGroup, id:Int, check : Boolean, sizeDp:Int){
    val rbS = RadioButton(this)
    rbS.id = id
    rbS.setButtonDrawable(android.R.color.transparent)
    val layoutM = RadioGroup.LayoutParams(uiTayDpToPx(sizeDp),uiTayDpToPx(sizeDp))
    layoutM.setMargins(uiTayDpToPx(sizeDp),0,0,0)
    rbS.layoutParams = layoutM
    rbS.isChecked = check
    radio.addView(rbS)

}
