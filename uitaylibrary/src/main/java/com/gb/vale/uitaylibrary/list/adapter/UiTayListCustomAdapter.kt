package com.gb.vale.uitaylibrary.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.databinding.UiTayListCustomBinding
import com.gb.vale.uitaylibrary.list.model.UiTayModelCustom
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayVisibility

class UiTayListCustomAdapter(var onClickOption: ((Int) -> Unit)? = null) :
    RecyclerView.Adapter<UiTayListCustomAdapter.UiTayListViewHolder>() {
    var list: List<UiTayModelCustom> = ArrayList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UiTayListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            UiTayListCustomBinding.inflate(layoutInflater, parent, false)
        return UiTayListViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UiTayListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class UiTayListViewHolder(private val binding: UiTayListCustomBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("NotifyDataSetChanged")
        fun bind(option: UiTayModelCustom) {
            binding.uiTayRowSubTitleListCustom.uiTayVisibility(option.subTitle.isNotEmpty())
            binding.uiTayRowMessageListCustom.uiTayVisibility(option.message.isNotEmpty())
            if (list.size-1 == adapterPosition)binding.lineCustom.visibility = View.GONE
            binding.root.setOnClickUiTayDelay { onClickOption?.invoke(adapterPosition) }
            binding.uiTayRowTitleListCustom.text = option.title
            binding.uiTayRowSubTitleListCustom.text = option.subTitle
            binding.uiTayRowMessageListCustom.text = option.message

        }

    }
}