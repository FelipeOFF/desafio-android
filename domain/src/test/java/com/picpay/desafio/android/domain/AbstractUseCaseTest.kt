package com.picpay.desafio.android.domain

import com.nhaarman.mockitokotlin2.spy
import com.picpay.desafio.android.domain.mock.MockUseCase
import com.picpay.desafio.android.domain.mock.MockUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import okhttp3.ResponseBody.Companion.toResponseBody
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class AbstractUseCaseTest {

    @Test
    fun `When calling a usecase, it first issues the loading`(): Unit = runBlocking {
        // Give
        val value = "Teste"

        val mockUseCase: MockUseCase = spy(MockUseCaseImpl())

        // When
        val resultFlow: Flow<ResultWrapper<String>> = mockUseCase(value)

        // Then
        val loading = resultFlow.toList()[0]

        // Verify
        assert(loading is ResultWrapper.Loading)
    }

    @Test
    fun `When calling some usecase it returns success along with Loading and DismissLoading`(): Unit = runBlocking {
        // Give
        val value = "Teste"

        val mockUseCase: MockUseCase = spy(MockUseCaseImpl())

        // When
        val resultFlowList: List<ResultWrapper<String>> = mockUseCase(value).toList()

        // Then
        val loading = resultFlowList[0]
        val success = resultFlowList[1]
        val dismissLoading = resultFlowList[2]

        // Verify
        assert(loading is ResultWrapper.Loading)
        assert(success is ResultWrapper.Success<String>)
        assert(dismissLoading is ResultWrapper.DismissLoading)

        assertEquals((success as? ResultWrapper.Success<String>)?.value, value)
    }

    @Test
    fun `when calling some UseCase it returns Unknown error`(): Unit = runBlocking {
        // Give
        val value = "Teste"
        val exception = Exception(value)

        val mockUseCase: MockUseCase = spy(MockUseCaseImpl(withThrowable = exception))

        // When
        val resultFlowList: List<ResultWrapper<String>> = mockUseCase(value).toList()

        // Then
        val loading = resultFlowList[0]
        val error = resultFlowList[1]
        val dismissLoading = resultFlowList[2]

        // Verify
        assert(loading is ResultWrapper.Loading)
        assert(error is ResultWrapper.Error)
        assert(dismissLoading is ResultWrapper.DismissLoading)

        assertEquals(((error as? ResultWrapper.Error)?.error as? ErrorWrapper.UnknownException)?.cause, exception)
    }

    @Test
    fun `when calling some UseCase it returns Http error`(): Unit = runBlocking {
        // Give
        val value = "Teste"
        val code = 401
        val exception = HttpException(Response.error<String>(code, value.toResponseBody()))

        val mockUseCase: MockUseCase = spy(MockUseCaseImpl(withThrowable = exception))

        // When
        val resultFlowList: List<ResultWrapper<String>> = mockUseCase(value).toList()

        // Then
        val loading = resultFlowList[0]
        val error = resultFlowList[1]
        val dismissLoading = resultFlowList[2]

        // Verify
        assert(loading is ResultWrapper.Loading)
        assert(error is ResultWrapper.Error)
        assert(dismissLoading is ResultWrapper.DismissLoading)

        assertEquals(((error as? ResultWrapper.Error)?.error as? ErrorWrapper.Server)?.cause, exception)
    }

    @Test
    fun `when calling some UseCase it returns UnknownHostException error`(): Unit = runBlocking {
        // Give
        val value = "Teste"
        val exception = UnknownHostException(value)

        val mockUseCase: MockUseCase = spy(MockUseCaseImpl(withThrowable = exception))

        // When
        val resultFlowList: List<ResultWrapper<String>> = mockUseCase(value).toList()

        // Then
        val loading = resultFlowList[0]
        val error = resultFlowList[1]
        val dismissLoading = resultFlowList[2]

        // Verify
        assert(loading is ResultWrapper.Loading)
        assert(error is ResultWrapper.Error)
        assert(dismissLoading is ResultWrapper.DismissLoading)

        assertEquals(((error as? ResultWrapper.Error)?.error as? ErrorWrapper.NetworkException)?.cause, exception)
    }

}