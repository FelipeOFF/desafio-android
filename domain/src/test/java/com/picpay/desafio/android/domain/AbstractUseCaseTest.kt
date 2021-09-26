package com.picpay.desafio.android.domain

import com.nhaarman.mockitokotlin2.spy
import com.picpay.desafio.android.domain.mock.MockUseCase
import com.picpay.desafio.android.domain.mock.MockUseCaseImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

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
    }

}