package com.picpay.desafio.android.domain.mock

import com.picpay.desafio.android.domain.AbstractUseCase
import kotlinx.coroutines.delay

abstract class MockUseCase : AbstractUseCase<String, String>()

class MockUseCaseImpl(
    private val withThrowable: Throwable? = null
) : MockUseCase() {
    override suspend fun execute(param: String): String {
        if (withThrowable == null) {
            delay(1000)
            return param
        } else {
            throw withThrowable
        }
    }
}