package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.ErrorWrapper
import com.picpay.desafio.android.domain.ResultWrapper
import com.picpay.desafio.android.domain.user.GetUserUseCase
import com.picpay.desafio.android.model.users.res.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.net.UnknownHostException

class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val getUserUserCase: GetUserUseCase = mock()

    @ObsoleteCoroutinesApi
    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ObsoleteCoroutinesApi
    @After
    @ExperimentalCoroutinesApi
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `When obtaining the user through UseCase the loading is shown`(): Unit = runBlocking {
        // Give
        val userNameMock = "Teste"
        val usersMock: List<User> = listOf(mock {
            whenever(mock.name).thenReturn(userNameMock)
        })

        val showLoadingObserverMock: Observer<Boolean> = mock()

        // When
        whenever(getUserUserCase(Unit)).doReturn(flow {
            emit(ResultWrapper.Loading)
            emit(ResultWrapper.Success(usersMock))
            emit(ResultWrapper.DismissLoading)
        })

        // Then
        val mainViewModel = MainViewModel(getUserUserCase)
        mainViewModel.showLoading.observeForever(showLoadingObserverMock)

        // Verify
        verify(showLoadingObserverMock, timeout(1000).times(2)).onChanged(true)
        verify(showLoadingObserverMock, timeout(1000).times(1)).onChanged(false)
    }

    @Test
    fun `When acquiring users through UseCase the list is filled with values`(): Unit = runBlocking {
        // Give
        val userNameMock = "Teste"
        val usersMock: List<User> = listOf(mock {
            whenever(mock.name).thenReturn(userNameMock)
        })

        val listOfUserObserverMock: Observer<List<User>> = mock()

        // When
        whenever(getUserUserCase(Unit)).doReturn(flow {
            emit(ResultWrapper.Loading)
            emit(ResultWrapper.Success(usersMock))
            emit(ResultWrapper.DismissLoading)
        })

        // Then
        val mainViewModel = MainViewModel(getUserUserCase)
        mainViewModel.listOfUser.observeForever(listOfUserObserverMock)

        // Verify
        verify(listOfUserObserverMock, timeout(1000).times(1)).onChanged(usersMock)
    }

    @Test
    fun `show error in case of connection failure`(): Unit = runBlocking {
        // Give
        val message = "Teste"
        val exception = UnknownHostException(message)

        val showLoadingObserverMock: Observer<Boolean> = mock()
        val showErrorObserverMock: Observer<Int> = mock()

        // When
        whenever(getUserUserCase(Unit)).doReturn(flow {
            emit(ResultWrapper.Loading)
            emit(ResultWrapper.Error(ErrorWrapper.NetworkException(exception)))
            emit(ResultWrapper.DismissLoading)
        })

        // Then
        val mainViewModel = MainViewModel(getUserUserCase)
        mainViewModel.showError.observeForever(showErrorObserverMock)
        mainViewModel.showLoading.observeForever(showLoadingObserverMock)

        // Verify
        verify(showErrorObserverMock, timeout(1000).times(1)).onChanged(null)
        verify(showErrorObserverMock, timeout(1000).times(2)).onChanged(R.string.error_connection)

        verify(showLoadingObserverMock, timeout(1000).times(1)).onChanged(true)
        verify(showLoadingObserverMock, timeout(1000).times(2)).onChanged(false)
    }

    @Test
    fun `show error in case of unknown failure`(): Unit = runBlocking {
        // Give
        val message = "Teste"
        val exception = Exception(message)

        val showErrorObserverMock: Observer<Int> = mock()

        // When
        whenever(getUserUserCase(Unit)).doReturn(flow {
            emit(ResultWrapper.Loading)
            emit(ResultWrapper.Error(ErrorWrapper.UnknownException(exception)))
            emit(ResultWrapper.DismissLoading)
        })

        // Then
        val mainViewModel = MainViewModel(getUserUserCase)
        mainViewModel.showError.observeForever(showErrorObserverMock)

        // Verify
        verify(showErrorObserverMock, timeout(1000).times(2)).onChanged(null)
        verify(showErrorObserverMock, timeout(1000).times(2)).onChanged(R.string.error)
    }

}