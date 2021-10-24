package com.example.mvijsonholder.data

import io.github.esentsov.PackagePrivate
import retrofit2.Response
import retrofit2.http.GET

@PackagePrivate
interface PostService {

    @GET("/posts")
    suspend fun getAllPosts(): Response<List<PostResponse>>
}
