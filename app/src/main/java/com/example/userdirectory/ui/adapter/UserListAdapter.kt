package com.example.userdirectory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.userdirectory.databinding.ItemUserBinding
import com.example.userdirectory.model.response.UserListResponseItem

class UserListAdapter(private val listener: ItemClickListener) :
    ListAdapter<UserListResponseItem, UserListAdapter.ViewHolder>(UserListAdapterDiffCallback()) {
    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserListResponseItem) {
            binding.apply {
                tvUserName.text = item.name
                tvEmail.text = item.email
                tvPhone.text = item.phone

                root.setOnClickListener {
                    listener.onDetailClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserListAdapterDiffCallback : DiffUtil.ItemCallback<UserListResponseItem>() {
        override fun areItemsTheSame(
            oldItem: UserListResponseItem,
            newItem: UserListResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserListResponseItem,
            newItem: UserListResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemClickListener {
        fun onDetailClick(user: UserListResponseItem)
    }

}

