package com.example.mvijsonholder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvijsonholder.common.BaseViewModel
import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.domain.GetPostUseCase
import com.example.mvijsonholder.domain.PostResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel<MainIntent>() {

    private val _posts = MutableLiveData<DataState<List<PostResult>>>()
    val posts: LiveData<DataState<List<PostResult>>>
        get() = _posts

    override fun onTrackingEvent(eventType: MainIntent) {
        when (eventType) {
            MainIntent.GetPosts -> getPosts()
        }
    }

    private fun getPosts() {
        getPostUseCase.execute()
            .flowOn(dispatcher)
            .catch {
                _posts.value = DataState.Error(it.message)
            }.onEach {
                _posts.value = it
            }.launchIn(viewModelScope)
    }
}
