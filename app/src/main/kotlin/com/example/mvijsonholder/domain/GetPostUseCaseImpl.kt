package com.example.mvijsonholder.domain

import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.data.PostRepository
import com.example.mvijsonholder.data.PostResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

interface GetPostUseCase {
    fun execute(): Flow<DataState<List<PostResult>>>
}

@ExperimentalCoroutinesApi
class GetPostUseCaseImpl(private val repository: PostRepository) : GetPostUseCase {
    override fun execute(): Flow<DataState<List<PostResult>>> {
        return repository.getAllPost().flatMapLatest { response ->
            flow {
                when (response) {
                    is DataState.Success -> emit(DataState.Success(data = response.data?.map { it.mapToDomain() }))
                    is DataState.Loading -> emit(DataState.Loading)
                    is DataState.Error -> emit(DataState.Error(response.message))
                    is DataState.Empty -> emit(DataState.Empty)
                }
            }
        }
    }
}

fun PostResponse.mapToDomain() = PostResult(body = body, id = id, title = title, userId = userId)
