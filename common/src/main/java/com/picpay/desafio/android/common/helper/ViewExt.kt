package com.picpay.desafio.android.common.helper

import android.opengl.Visibility
import android.view.View
import androidx.databinding.BindingAdapter

fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.hide() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

@BindingAdapter("show")
fun View.showFromXml(value: Boolean?) {
    if (value == true) {
        show()
    } else {
        hide()
    }
}