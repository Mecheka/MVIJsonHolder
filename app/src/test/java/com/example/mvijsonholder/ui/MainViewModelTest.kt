package com.example.mvijsonholder.ui

import androidx.lifecycle.Observer
import com.example.mvijsonholder.InstantExecutorExtension
import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.domain.GetPostUseCase
import com.example.mvijsonholder.domain.PostRepository
import com.example.mvijsonholder.domain.PostResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    private val repository: PostRepository = mock()
    private var getPostUseCase: GetPostUseCase? = null
    private var viewModel: MainViewModel? = null
    private val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getPostUseCase = GetPostUseCase(repository)
        viewModel = MainViewModel(getPostUseCase!!)
    }

    @AfterEach
    fun down() {
        Dispatchers.resetMain()
        reset(repository)
        getPostUseCase = null
        viewModel = null
    }

    @Test
    fun `Get posts success`() {
        whenever(repository.getAllPost()).thenReturn(
            flow {
                emit(
                    DataState.Success(
                        listOf(
                            PostResult(
                                body = "test",
                                id = 0,
                                title = "test",
                                userId = 0
                            )
                        )
                    )
                )
            }
        )

        val observer = mock<Observer<DataState<List<PostResult>>>>()
        viewModel?.posts?.observeForever(observer)
        viewModel?.onTrackingEvent(MainIntent.GetPosts)

        verify(observer).onChanged(
            DataState.Success(
                listOf(
                    PostResult(
                        body = "test",
                        id = 0,
                        title = "test",
                        userId = 0
                    )
                )
            )
        )
    }

    @Test
    fun `Get posts fail`() {
        whenever(repository.getAllPost()).thenReturn(
            flow {
                throw Exception("test")
            }
        )

        val observer = mock<Observer<DataState<List<PostResult>>>>()
        viewModel?.posts?.observeForever(observer)
        viewModel?.onTrackingEvent(MainIntent.GetPosts)

        verify(observer).onChanged(
            DataState.Error("test")
        )
    }
}
