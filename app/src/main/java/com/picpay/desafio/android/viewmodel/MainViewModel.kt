package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.picpay.desafio.android.R
import com.picpay.desafio.android.common.viewmodel.BaseViewModel
import com.picpay.desafio.android.domain.ErrorWrapper
import com.picpay.desafio.android.domain.ResultWrapper
import com.picpay.desafio.android.domain.user.GetUserUseCase
import com.picpay.desafio.android.model.users.res.User
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val userUserCase: GetUserUseCase
) : BaseViewModel() {

    private val commandsChannelUserResult = Channel<Boolean>()

    private val userResultWrapper: LiveData<ResultWrapper<List<User>>> = liveData {
        commandsChannelUserResult.consumeEach {
            emitSource(userUserCase(Unit).asLiveData(Main))
        }
    }

    val showLoading: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(userResultWrapper) { result ->
            value = loadingByResultWrapper(result)
        }
    }

    val listOfUser: LiveData<List<User>> = MediatorLiveData<List<User>>().apply {
        addSource(userResultWrapper) { result ->
            listOfUsersByResultWrapper(result)
        }
    }

    val showError: MutableLiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(userResultWrapper) { result ->
            showErrorByResultWrapper(result)
        }
    }

    init {
        onClickInTryAgain()
    }

    fun onClickInTryAgain() {
        launch {
            showError.value = null
            commandsChannelUserResult.send(true)
        }
    }

    private fun MediatorLiveData<Int>.showErrorByResultWrapper(result: ResultWrapper<List<User>>?) {
        value = when (result) {
            is ResultWrapper.Success -> null
            is ResultWrapper.Error -> {
                when (result.error) {
                    is ErrorWrapper.NetworkException -> R.string.error_connection
                    is ErrorWrapper.Server -> R.string.error
                    is ErrorWrapper.UnknownException -> R.string.error
                }
            }
            else -> value
        }
    }

    private fun MediatorLiveData<List<User>>.listOfUsersByResultWrapper(
        result: ResultWrapper<List<User>>?
    ) {
        if (result is ResultWrapper.Success<List<User>>) {
            value = result.value
        }
    }

    private fun MediatorLiveData<Boolean>.loadingByResultWrapper(result: ResultWrapper<List<User>>?) =
        when (result) {
            is ResultWrapper.Loading -> true
            is ResultWrapper.DismissLoading -> false
            is ResultWrapper.Error -> false
            else -> value
        }

}
