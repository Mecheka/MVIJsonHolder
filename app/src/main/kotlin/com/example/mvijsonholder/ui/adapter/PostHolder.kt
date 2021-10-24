package com.example.mvijsonholder.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvijsonholder.databinding.ItemPostBinding
import com.example.mvijsonholder.domain.PostResult

class PostHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: PostResult) {
        with(binding) {
            textTitle.text = post.title
            textBody.text = post.body
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostHolder {
            val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostHolder(binding)
        }
    }
}