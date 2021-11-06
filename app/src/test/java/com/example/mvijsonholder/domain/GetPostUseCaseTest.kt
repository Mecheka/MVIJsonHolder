package com.example.mvijsonholder.domain

import com.example.mvijsonholder.common.DataState
import com.example.mvijsonholder.data.PostRepository
import com.example.mvijsonholder.data.PostResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetPostUseCaseTest {
    private val repository: PostRepository = mock()
    private lateinit var useCase: GetPostUseCase

    @BeforeEach
    fun setup() {
        useCase = GetPostUseCaseImpl(repository)
    }

    @AfterEach
    fun tearDown() {
        reset(repository)
    }

    @Test
    fun `Check mapping domain getAllPost success`() {
        runBlockingTest {
            whenever(repository.getAllPost()).thenReturn(
                flowOf(
                    DataState.Loading,
                    DataState.Success(
                        listOf(
                            PostResponse(
                                body = "",
                                id = 0,
                                title = "",
                                userId = 0
                            )
                        )
                    )
                )
            )

            val ac = useCase.execute()
            Assertions.assertIterableEquals(
                listOf(
                    DataState.Loading,
                    DataState.Success(
                        listOf(
                            PostResult(
                                body = "",
                                id = 0,
                                title = "",
                                userId = 0
                            )
                        )
                    )
                ),
                ac.toList()
            )
        }
    }

    @Test
    fun `Check mapping domain getAllPost error`() {
        runBlockingTest {
            whenever(repository.getAllPost()).thenReturn(
                flowOf(
                    DataState.Loading,
                    DataState.Error("Test")
                )
            )

            val ac = useCase.execute()
            Assertions.assertIterableEquals(
                listOf(DataState.Loading, DataState.Error("Test")),
                ac.toList()
            )
        }
    }
}
