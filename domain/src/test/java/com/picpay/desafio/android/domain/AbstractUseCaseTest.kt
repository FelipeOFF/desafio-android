package com.picpay.desafio.android.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.mock.MockUseCaseImpl
import com.picpay.desafio.android.domain.mock.MockUseCaseImpl.Companion.TEST_CACHE
import com.picpay.desafio.android.repository.cache.Cache
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import okhttp3.ResponseBody.Companion.toResponseBody
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class AbstractUseCaseTest {

    @Test
    fun `When calling a usecase, it first issues the loading`(): Unit = runBlocking {
        // Give
        val value = "Teste"

        val mockUseCase = spy(MockUseCaseImpl())

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

        val mockUseCase = spy(MockUseCaseImpl())

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
    fun `When calling some usecase it returns success along with Loading DismissLoading and execute return by cache`(): Unit = runBlocking {
        // Give
        val value = "Teste"
        val cache: Cache<String> = mock()

        val mockUseCase = spy(MockUseCaseImpl(cache = cache))

        // When
        whenever(cache.get(eq(TEST_CACHE), anyBoolean(), any())).doReturn(value)

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

        val mockUseCase = spy(MockUseCaseImpl(withThrowable = exception))

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

        assertEquals(((error as? ResultWrapper.Error)?.error as? ErrorWrapper.UnknownException)?.cause?.message, exception.message)
    }

    @Test
    fun `when calling some UseCase it returns Http error`(): Unit = runBlocking {
        // Give
        val value = "Teste"
        val code = 401
        val exception = HttpException(Response.error<String>(code, value.toResponseBody()))

        val mockUseCase = spy(MockUseCaseImpl(withThrowable = exception))

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

        val mockUseCase = spy(MockUseCaseImpl(withThrowable = exception))

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

        assertEquals(((error as? ResultWrapper.Error)?.error as? ErrorWrapper.NetworkException)?.cause?.message, exception.message)
    }

}