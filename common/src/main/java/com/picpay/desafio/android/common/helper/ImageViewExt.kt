package com.picpay.desafio.android.common.helper

import android.widget.ImageView
import android.widget.ProgressBar
import com.picpay.desafio.android.common.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadImg(imageURL: String, progressBar: ProgressBar? = null) {
    Picasso.get()
        .load(imageURL)
        .error(R.drawable.ic_round_account_circle)
        .into(this, object : Callback {
            override fun onSuccess() {
                progressBar?.hide()
            }

            override fun onError(e: Exception?) {
                progressBar?.hide()
            }
        })
}