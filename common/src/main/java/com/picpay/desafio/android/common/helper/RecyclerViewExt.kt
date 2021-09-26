package com.picpay.desafio.android.common.helper

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.common.adapter.AbstractAdapterItems

@BindingAdapter("items")
fun RecyclerView.setItems(list: List<Any>?) {
    list?.let { listLet ->
        (adapter as? AbstractAdapterItems)?.replaceItems(listLet)
    }
}