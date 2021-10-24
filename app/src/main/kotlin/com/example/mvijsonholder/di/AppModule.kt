package com.example.mvijsonholder.di

import com.example.mvijsonholder.data.PostRepositoryImpl
import com.example.mvijsonholder.data.PostService
import com.example.mvijsonholder.domain.GetPostUseCase
import com.example.mvijsonholder.domain.PostRepository
import com.example.mvijsonholder.manager.RetrofitClient
import com.example.mvijsonholder.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { RetrofitClient.createClient() }

    factory { get<Retrofit>().create(PostService::class.java) }

    factory<PostRepository> { PostRepositoryImpl(get()) }

    factory { GetPostUseCase(get()) }

    viewModel { MainViewModel(get()) }
}
