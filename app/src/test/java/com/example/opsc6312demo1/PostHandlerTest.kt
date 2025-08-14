package com.example.opsc6312demo1

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PostHandlerTest {

    private lateinit var api: ApiService
    private lateinit var handler: PostHandler

    @Before
    fun setup() {
        api = mock(ApiService::class.java)
        handler = PostHandler(api)
    }

    @Test
    fun `fetchPost returns success when API call succeeds`() = runBlocking {

        val fakePost = Post(1, 1, "Title", "Body")
        `when`(api.getPost()).thenReturn(fakePost)


        val result = handler.fetchPost()


        assertTrue(result.isSuccess)
        assertEquals(fakePost, result.getOrNull())
    }

    @Test
    fun `fetchPost returns failure when API call throws exception`() = runBlocking {

        `when`(api.getPost()).thenThrow(RuntimeException("Network Error"))


        val result = handler.fetchPost()


        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }
}
