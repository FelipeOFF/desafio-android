package com.picpay.desafio.android.domain.mock

import com.picpay.desafio.android.domain.AbstractUseCase
import kotlinx.coroutines.delay

abstract class MockUseCase : AbstractUseCase<String, String>()

class MockUseCaseImpl : MockUseCase() {
    override suspend fun execute(param: String): String {
        delay(1000)
        return param
    }
}