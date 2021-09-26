package com.picpay.desafio.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.common.adapter.AbstractAdapterItems
import com.picpay.desafio.android.model.users.res.User
import com.picpay.desafio.android.util.safeHeritage

class UserListAdapter : RecyclerView.Adapter<UserListItemViewHolder>(), AbstractAdapterItems {

    private val users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    override fun replaceItems(list: List<Any>) =
        setItems(list.safeHeritage())

    private fun setItems(listOfUsers: List<User>) {
        val result = DiffUtil.calculateDiff(
            UserListDiffCallback(
                listOfUsers,
                users
            )
        )
        result.dispatchUpdatesTo(this)
        users.clear()
        users.addAll(listOfUsers)
    }
}