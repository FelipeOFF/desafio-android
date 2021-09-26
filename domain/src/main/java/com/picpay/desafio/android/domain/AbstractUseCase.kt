package com.picpay.desafio.android.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class AbstractUseCase<in PARAMETER, out RESULT> {

    protected abstract suspend fun execute(param: PARAMETER): RESULT

    open operator fun invoke(value: PARAMETER): Flow<ResultWrapper<RESULT>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val result = execute(value)
        } finally {
            emit(ResultWrapper.DismissLoading)
        }
    }

}
