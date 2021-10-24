package com.example.mvijsonholder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvijsonholder.common.BaseViewModel
import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.domain.GetPostUseCase
import com.example.mvijsonholder.domain.PostResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainViewModel(private val getPostUseCase: GetPostUseCase) : BaseViewModel<MainIntent>() {

    private val _posts = MutableLiveData<DataState<List<PostResult>>>(DataState.Loading)
    val posts: LiveData<DataState<List<PostResult>>>
        get() = _posts

    override fun onTrackingEvent(eventType: MainIntent) {
        when (eventType) {
            MainIntent.GetPosts -> getPosts()
        }
    }

    private fun getPosts() {
        getPostUseCase.execute()
            .flowOn(Dispatchers.IO)
            .catch {
                _posts.value = DataState.Error(it.message)
            }.onEach {
                _posts.value = it
            }.launchIn(viewModelScope)
    }
}
