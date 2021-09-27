package com.picpay.desafio.android.repository

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.gateway.service.PicPayService
import com.picpay.desafio.android.model.users.res.User
import com.picpay.desafio.android.repository.user.PicPayUserRepository
import com.picpay.desafio.android.repository.user.PicPayUserRepositoryImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class PicPayUserRepositoryTest {

    private val picPayService: PicPayService = mock()

    private lateinit var picPayUserRepository: PicPayUserRepository

    @Before
    fun setup() {
        picPayUserRepository = PicPayUserRepositoryImpl(
            picPayService = picPayService
        )
    }

    @Test
    fun `making request to get the user list successfully`(): Unit = runBlocking {
        // Give
        val testUserName = "Teste"
        val listOfUserMock: List<User> = listOf(
            mock {
                whenever(mock.name).thenReturn(testUserName)
            }
        )

        // When
        whenever(picPayService.getUsersSuspend()).thenReturn(listOfUserMock)

        // Then
        val result = picPayUserRepository.getListOfUsers()

        assertEquals(listOfUserMock, result)
        assertEquals(listOfUserMock[0].name, result[0].name)

        // Verify
        verify(picPayService, times(1)).getUsersSuspend()
    }

    @Test
    fun `making a request and hears an error`(): Unit = runBlocking {
        // Give
        val code = 401
        val httpException = HttpException(Response.error<List<User>>(code, "Error".toResponseBody()))

        // When
        whenever(picPayService.getUsersSuspend()).doThrow(httpException)

        // Then
        try {
            val result = picPayUserRepository.getListOfUsers()
            assertNull(result)
        } catch (e: HttpException) {
            assertEquals(e.code(), code)
            assertEquals(e, httpException)
        }

        // Verify
        verify(picPayService, times(1)).getUsersSuspend()
    }

}