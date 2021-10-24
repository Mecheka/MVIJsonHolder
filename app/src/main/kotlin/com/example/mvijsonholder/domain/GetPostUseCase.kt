package com.example.mvijsonholder.domain

import com.example.mvijsonholder.common.DataState
import kotlinx.coroutines.flow.Flow

class GetPostUseCase(private val repository: PostRepository) {

    fun execute(): Flow<DataState<List<PostResult>>> {
        return repository.getAllPost()
    }
}
