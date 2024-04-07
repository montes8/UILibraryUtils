package com.gb.vale.uitaylibrary.list.model

import com.gb.vale.uitaylibrary.utils.UI_TAY_EMPTY

data class UiTayModelCustom(
    val image : Int =0,
    val imageUrl : String = UI_TAY_EMPTY,
    val imageVisibility : Boolean =false,
    val imageCircle : Boolean =false,
    val title : String = UI_TAY_EMPTY,
    val subTitle : String = UI_TAY_EMPTY,
    val message : String = UI_TAY_EMPTY
)