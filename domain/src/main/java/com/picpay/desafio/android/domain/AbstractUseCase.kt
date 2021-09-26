package com.picpay.desafio.android.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException

abstract class AbstractUseCase<in PARAMETER, out RESULT> {

    protected abstract suspend fun execute(param: PARAMETER): RESULT

    open operator fun invoke(value: PARAMETER): Flow<ResultWrapper<RESULT>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val result = execute(value)
            emit(ResultWrapper.Success(result))
        } catch (e: UnknownHostException) {
            emit(ResultWrapper.Error(ErrorWrapper.NetworkException(cause = e)))
        } catch (e: HttpException) {
            emit(
                ResultWrapper.Error(
                    ErrorWrapper.Server(
                        code = e.code(),
                        message = e.message(),
                        cause = e
                    )
                )
            )
        } catch (e: Throwable) {
            emit(ResultWrapper.Error(ErrorWrapper.UnknownException(e)))
        } finally {
            emit(ResultWrapper.DismissLoading)
        }
    }

}
