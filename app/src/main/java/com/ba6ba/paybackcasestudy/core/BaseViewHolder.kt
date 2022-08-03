package com.ba6ba.paybackcasestudy.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(item: T, position: Int)
}