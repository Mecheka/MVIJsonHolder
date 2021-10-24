package com.example.mvijsonholder.data

import com.example.mvijsonholder.domain.PostResult
import com.google.gson.annotations.SerializedName
import io.github.esentsov.PackagePrivate

@PackagePrivate
data class PostResponse(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)

fun PostResponse.mapToDomain() = PostResult(body = body, id = id, title = title, userId = userId)
