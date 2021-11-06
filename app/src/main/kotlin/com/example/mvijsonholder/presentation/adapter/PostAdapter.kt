package com.example.mvijsonholder.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mvijsonholder.domain.PostResult

class PostAdapter : ListAdapter<PostResult, PostHolder>(PostDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        return PostHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
