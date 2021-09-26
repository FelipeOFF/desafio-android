package com.picpay.desafio.android.domain.mock

import com.picpay.desafio.android.domain.AbstractUseCase
import com.picpay.desafio.android.repository.cache.Cache
import kotlinx.coroutines.delay

class MockUseCaseImpl(
    private val withThrowable: Throwable? = null,
    cache: Cache<String>? = null
) : AbstractUseCase<String, String>(
    cache = cache,
    key = TEST_CACHE
) {
    override suspend fun execute(param: String): String {
        if (withThrowable == null) {
            delay(1000)
            return param
        } else {
            throw withThrowable
        }
    }

    companion object {
        const val TEST_CACHE = "test_cache"
    }
}