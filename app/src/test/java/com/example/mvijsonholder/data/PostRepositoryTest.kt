package com.example.mvijsonholder.data

import com.example.mvijsonholder.common.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class PostRepositoryTest {

    private val service: PostService = mock()
    private val dispatcher = TestCoroutineDispatcher()
    private var repository: PostRepository? = null

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = PostRepositoryImpl(service)
    }

    @AfterEach
    fun down() {
        Dispatchers.resetMain()
        reset(service)
        repository = null
    }

    @Test
    fun `Call service post success`() {
        dispatcher.runBlockingTest {
            whenever(service.getAllPosts()).thenReturn(
                Response.success(
                    listOf(
                        PostResponse(
                            body = "test",
                            id = 0,
                            title = "test",
                            userId = 0
                        )
                    )
                )
            )

            val actual = repository?.getAllPost()?.toList()?.toTypedArray()
            val expected = arrayOf(
                DataState.Loading,
                DataState.Success(
                    data = listOf(
                        PostResponse(
                            body = "test",
                            id = 0,
                            title = "test",
                            userId = 0
                        )
                    )
                )
            )
            assertArrayEquals(
                expected,
                actual
            )
        }
    }

    @Test
    fun `Call service post fail`() {
        dispatcher.runBlockingTest {
            whenever(service.getAllPosts()).thenReturn(
                Response.error(404, ResponseBody.create(MediaType.parse("plain"), "test"))
            )

            val actual = repository?.getAllPost()?.toList()?.toTypedArray()
            val expected = arrayOf(
                DataState.Loading,
                DataState.Error("Response.error()")
            )
            assertArrayEquals(
                expected,
                actual
            )
        }
    }

    @Test
    fun `Call service post error`() {
        dispatcher.runBlockingTest {
            whenever(service.getAllPosts()).doThrow(RuntimeException("test"))

            val actual = repository?.getAllPost()?.toList()?.toTypedArray()
            val expected = arrayOf(
                DataState.Loading,
                DataState.Error("test")
            )
            assertArrayEquals(
                expected,
                actual
            )
        }
    }
}
