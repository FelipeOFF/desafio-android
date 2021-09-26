package com.picpay.desafio.android.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.common.helper.loadImg
import com.picpay.desafio.android.model.users.res.User
import kotlinx.android.synthetic.main.list_item_user.view.name
import kotlinx.android.synthetic.main.list_item_user.view.picture
import kotlinx.android.synthetic.main.list_item_user.view.progressBar
import kotlinx.android.synthetic.main.list_item_user.view.username

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        itemView.name.text = user.name
        itemView.username.text = user.username
        itemView.progressBar.visibility = View.VISIBLE
        itemView.picture.loadImg(user.img, itemView.progressBar)
    }
}