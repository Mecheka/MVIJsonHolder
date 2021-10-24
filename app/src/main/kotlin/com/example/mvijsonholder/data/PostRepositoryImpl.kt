package com.example.mvijsonholder.data

import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.domain.PostRepository
import com.example.mvijsonholder.domain.PostResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(private val service: PostService) : PostRepository {
    override fun getAllPost(): Flow<DataState<List<PostResult>>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = service.getAllPosts()
                if (response.isSuccessful) {
                    val body = response.body()
                    emit(DataState.Success(data = body?.map { it.mapToDomain() }))
                } else {
                    emit(DataState.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(DataState.Error(e.message))
            }
        }
    }
}
