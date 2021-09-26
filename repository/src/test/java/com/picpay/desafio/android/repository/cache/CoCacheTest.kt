package com.picpay.desafio.android.repository.cache

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.repository.cache.store.CacheStore
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoCacheTest {

    private val cacheStoreMock: CacheStore<String> = mock()

    private val cache: Cache<String> = spy(CoCache(cacheStoreMock))

    @Test
    fun `when requesting get from cache returns the stored value`(): Unit = runBlocking {
        // Give
        val key = "Teste"
        val wrongResult = "ResultWrong"
        val rightResult = "result"

        // When
        whenever(cacheStoreMock.get(key)).doReturn(rightResult)

        // Then
        val result = cache.get(key) {
            delay(1000)
            wrongResult
        }

        assertEquals(result, rightResult)

        // Verify
        verify(cacheStoreMock, times(1)).get(key)
    }

    @Test
    fun `when requesting get from cache returns the value of the request because it doesn't exist yet`(): Unit = runBlocking {
        // Give
        val key = "Teste"
        val rightResult = "result"

        // When
        whenever(cacheStoreMock.get(key)).doReturn(null)
        whenever(cacheStoreMock.save(key, rightResult)).doReturn(Unit)

        // Then
        val result = cache.get(key) {
            delay(1000)
            rightResult
        }

        assertEquals(result, rightResult)

        // Verify
        verify(cacheStoreMock, times(1)).get(key)
        verify(cacheStoreMock, times(1)).save(key, rightResult)
    }

    @Test
    fun `when requesting get from cache returns the value of the request because it is forced`(): Unit = runBlocking {
        // Give
        val key = "Teste"
        val wrongResult = "ResultWrong"
        val rightResult = "result"

        // When
        whenever(cacheStoreMock.get(key)).doReturn(wrongResult)
        whenever(cacheStoreMock.save(key, rightResult)).doReturn(Unit)

        // Then
        val result = cache.get(key, forceLoad = true) {
            delay(1000)
            rightResult
        }

        assertEquals(result, rightResult)

        // Verify
        verify(cacheStoreMock, times(1)).get(key)
        verify(cacheStoreMock, times(1)).save(key, rightResult)
    }

}