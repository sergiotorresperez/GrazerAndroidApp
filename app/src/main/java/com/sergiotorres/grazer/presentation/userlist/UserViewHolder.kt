package com.sergiotorres.grazer.presentation.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sergiotorres.grazer.R
import com.sergiotorres.grazer.databinding.ItemUserBinding
import com.sergiotorres.grazer.domain.model.User

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val glideRequestManager: RequestManager
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User) {
        with(binding) {
            nameTextView.text = item.name

            dobTextView.text = item.dateOfBirth.toString("MM dd yyyy")

            glideRequestManager
                .load(item.profileImage)
                .centerCrop()
                .placeholder(R.drawable.img_user_placeholder)
                .into(portraitImageView)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glideRequestManager: RequestManager
        ): UserViewHolder {
            return UserViewHolder(
                glideRequestManager = glideRequestManager,
                binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}