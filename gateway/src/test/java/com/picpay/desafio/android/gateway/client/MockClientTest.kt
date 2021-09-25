package com.picpay.desafio.android.gateway.client

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.gateway.service.PicPayService
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import retrofit2.Retrofit

class MockClientTest {

    private val retrofitMock: Retrofit = mock()

    @Test
    fun `when purchasing the retrofit client, set the correct mock URL`() {
        // Give
        val mockClient = spy(MockClient())

        // When
        whenever(mockClient.getRetrofit()).doReturn(lazy { retrofitMock })

        // Then
        val picpayService: PicPayService = mockClient.service

        assertNotNull(picpayService)

        // Verify
        verify(mockClient, times(1)).service
    }

}