package com.gb.vale.uitaylibrary.utils

import android.content.Context
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.dialog.UiTayDialog
import com.gb.vale.uitaylibrary.dialog.UiTayDialogLayout
import com.gb.vale.uitaylibrary.dialog.UiTayDialogLayoutBlock
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModelCustom
import com.gb.vale.uitaylibrary.swipe.UiTayCardSwipeButton
import com.gb.vale.uitaylibrary.swipe.UiTayCardSwipeHelper
import java.util.ArrayList

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


fun AppCompatActivity.showUiTayDialog(
    image: Int = R.drawable.ui_tay_ic_info, title: String = UI_TAY_DIALOG_TITLE,
    subTitle: String = UI_TAY_DIALOG_SUB_TITLE,
    buttonText: String = UI_TAY_DIALOG_ACCEPT, buttonCancelText: String = UI_TAY_DIALOG_CANCEL,
    btnCancel: Boolean = false, isCancel: Boolean = true,
    styleCustom : UiTayDialogModelCustom = UiTayDialogModelCustom(),
    func: ((action: Boolean) -> Unit)? = null,
) {
    val model = UiTayDialogModel(
        image= image, title= title, subTitle= subTitle,
        buttonText=buttonText, buttonCancelText =buttonCancelText,
         btnCancel=  btnCancel, isCancel = isCancel,styleCustom = styleCustom
    )
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
    addSwipeButtons: (MutableList<UiTayCardSwipeButton>) -> Unit
) {
    object : UiTayCardSwipeHelper(context, this, buttonWidth,marginStart) {
        override fun instanceCardSwipe(
            viewHolder: RecyclerView.ViewHolder,
            buffer: MutableList<UiTayCardSwipeButton>
        ) {
            addSwipeButtons(buffer)
        }
    }
}

