package com.example.mvijsonholder.data

import com.example.mvijsonholder.common.DataState
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getAllPost(): Flow<DataState<List<PostResponse>>>
}
