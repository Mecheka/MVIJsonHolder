package com.example.mvijsonholder.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mvijsonholder.domain.PostResult

class PostDiffUtil : DiffUtil.ItemCallback<PostResult>() {
    override fun areItemsTheSame(oldItem: PostResult, newItem: PostResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostResult, newItem: PostResult): Boolean {
        return oldItem == newItem
    }
}
