package com.example.userdirectory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.userdirectory.databinding.ItemPostBinding
import com.example.userdirectory.model.response.UserPostResponseItem

class UserPostAdapter() :
    ListAdapter<UserPostResponseItem, UserPostAdapter.ViewHolder>(UserPostDiffCallback()) {
    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserPostResponseItem) {
            binding.apply {
                tvTitle.text = item.title
                tvBody.text = item.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserPostDiffCallback : DiffUtil.ItemCallback<UserPostResponseItem>() {
        override fun areItemsTheSame(
            oldItem: UserPostResponseItem,
            newItem: UserPostResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserPostResponseItem,
            newItem: UserPostResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}

