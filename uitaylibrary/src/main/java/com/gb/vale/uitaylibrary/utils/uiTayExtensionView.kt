package com.gb.vale.uitaylibrary.utils

import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.R
import com.gb.vale.uitaylibrary.dialog.UiTayDialog
import com.gb.vale.uitaylibrary.dialog.UiTayDialogLayout
import com.gb.vale.uitaylibrary.dialog.UiTayDialogLayoutBlock
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel

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
    func: ((action: Boolean) -> Unit)? = null,
) {
    val model = UiTayDialogModel(
        image= image, title= title, subTitle= subTitle,
        buttonText=buttonText, buttonCancelText =buttonCancelText,
         btnCancel=  btnCancel, isCancel = isCancel
    )
    val dialog = UiTayDialog.newInstance(model)
    dialog.dialog?.setCancelable(model.isCancel)
    dialog.isCancelable = model.isCancel
    dialog.func = { func?.invoke(it) }
    dialog.show(this.supportFragmentManager, UiTayDialog::class.java.name)
}

