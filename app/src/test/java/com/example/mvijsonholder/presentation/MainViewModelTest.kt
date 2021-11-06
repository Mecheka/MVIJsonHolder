package com.example.mvijsonholder.presentation

import com.example.mvijsonholder.InstantExecutorExtension
import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.domain.GetPostUseCase
import com.example.mvijsonholder.domain.PostResult
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    private val getPostUseCase: GetPostUseCase = mock()
    private var viewModel: MainViewModel? = null
    private val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = MainViewModel(getPostUseCase, dispatcher)
    }

    @AfterEach
    fun down() {
        dispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
        reset(getPostUseCase)
        viewModel = null
    }

    @Test
    fun `Get posts success`() {
        whenever(getPostUseCase.execute()).thenReturn(
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

        viewModel?.onTrackingEvent(MainIntent.GetPosts)

        viewModel?.posts?.test()
            ?.assertValue(
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
        whenever(getPostUseCase.execute()).thenReturn(
            flow {
                emit(DataState.Error("test"))
            }
        )

        viewModel?.onTrackingEvent(MainIntent.GetPosts)

        viewModel?.posts?.test()
            ?.assertValue(DataState.Error("test"))
    }

    @Test
    fun `Get posts throw error`() {
        whenever(getPostUseCase.execute()).thenReturn(flow { throw Exception("test") })

        viewModel?.onTrackingEvent(MainIntent.GetPosts)

        viewModel?.posts?.test()
            ?.assertValue(DataState.Error("test"))
    }
}
