package com.picpay.desafio.android.domain.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.ErrorWrapper
import com.picpay.desafio.android.domain.ResultWrapper
import com.picpay.desafio.android.model.users.res.User
import com.picpay.desafio.android.repository.user.PicPayUserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class GetUserUseCaseTest {

    private val userRepository: PicPayUserRepository = mock()

    @Test
    fun `getting the list of users through usecase`(): Unit = runBlocking {
        // Give
        val userListMock: List<User> = listOf(mock())

        val userUserCase = GetUserUseCase(userRepository)

        // When
        whenever(userRepository.getListOfUsers()).thenReturn(userListMock)

        // Then
        val result = userUserCase(Unit).toList()

        // Verify
        assert(result[0] is ResultWrapper.Loading)
        assert(result[1] is ResultWrapper.Success)
        assert(result[2] is ResultWrapper.DismissLoading)

        assertEquals((result[1] as? ResultWrapper.Success<List<User>>)?.value, userListMock)
    }

    @Test
    fun `getting the list of users through usecase and returned error`(): Unit = runBlocking {
        // Give
        val value = "Teste"
        val code = 401
        val exception = HttpException(Response.error<List<User>>(code, value.toResponseBody()))

        val userUserCase = GetUserUseCase(userRepository)

        // When
        whenever(userRepository.getListOfUsers()).thenThrow(exception)

        // Then
        val result = userUserCase(Unit).toList()

        // Verify
        assert(result[0] is ResultWrapper.Loading)
        assert(result[1] is ResultWrapper.Error)
        assert(result[2] is ResultWrapper.DismissLoading)

        assertEquals(((result[1] as? ResultWrapper.Error)?.error as? ErrorWrapper.Server)?.cause, exception)
    }

}