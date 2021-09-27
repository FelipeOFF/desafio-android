package com.picpay.desafio.android.common.helper

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import com.picpay.desafio.android.common.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadImg(imageURL: String?, @DrawableRes imageErrorResource: Int, progressBar: ProgressBar? = null) {
    if (imageURL != null) {
        progressBar?.show()
        Picasso.get()
            .load(imageURL)
            .error(imageErrorResource)
            .into(this, object : Callback {
                override fun onSuccess() {
                    progressBar?.hide()
                }

                override fun onError(e: Exception?) {
                    progressBar?.hide()
                }
            })
    } else {
        setImageResource(imageErrorResource)
        progressBar?.hide()
    }
}