package com.sergiotorres.grazer.presentation.userlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.sergiotorres.grazer.domain.model.User

class UserListAdapter(
    private val glideRequestManager: RequestManager
) : ListAdapter<User, UserViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent, glideRequestManager)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }
}

