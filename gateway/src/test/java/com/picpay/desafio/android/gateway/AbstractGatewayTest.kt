package com.picpay.desafio.android.gateway

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.gateway.mock.AbstractGatewayClientMock
import junit.framework.TestCase.assertEquals
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AbstractGatewayTest {

    private val httpClientBuilder: OkHttpClient.Builder = mock()
    private val retrofitClientBuilder: Retrofit.Builder = mock()
    private val httpLoggingInterceptor: HttpLoggingInterceptor = mock()
    private val retrofitMock: Retrofit = mock()
    private val okHttpClientMock: OkHttpClient = mock()
    private val urls = URLs.URL_PROD

    @Before
    fun setup() {
        // When defaults
        whenever(httpClientBuilder.addInterceptor(httpLoggingInterceptor)).doReturn(httpClientBuilder)
        whenever(httpClientBuilder.connectTimeout(AbstractGateway.OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)).doReturn(httpClientBuilder)
        whenever(httpClientBuilder.readTimeout(AbstractGateway.OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)).doReturn(httpClientBuilder)
        whenever(httpClientBuilder.writeTimeout(AbstractGateway.OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)).doReturn(httpClientBuilder)
        whenever(httpClientBuilder.build()).doReturn(okHttpClientMock)
        whenever(retrofitClientBuilder.client(okHttpClientMock)).doReturn(retrofitClientBuilder)
        whenever(retrofitClientBuilder.baseUrl(urls.url)).doReturn(retrofitClientBuilder)
        whenever(retrofitClientBuilder.addConverterFactory(any<GsonConverterFactory>())).doReturn(retrofitClientBuilder)
        whenever(retrofitClientBuilder.build()).doReturn(retrofitMock)
    }

    @Test
    fun `when calling getRetrofit and the retrofit instance is created along with OKHttpClient with http log`() {
        // Given
        val abstractGateway = spy(AbstractGatewayClientMock(
            urls = urls,
            httpClientBuilder = httpClientBuilder,
            retrofitClientBuilder = retrofitClientBuilder,
            httpLoggingInterceptor = httpLoggingInterceptor,
            enableHttpLogging = true
        ))

        // When
        whenever(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).doReturn(httpLoggingInterceptor)

        val retrofitLazy = abstractGateway.getRetrofit()

        // Then
        assertEquals(retrofitLazy.value, retrofitMock)

        // Verify
        verify(httpLoggingInterceptor, times(1)).setLevel(HttpLoggingInterceptor.Level.BODY)
        verify(httpClientBuilder, times(1)).addInterceptor(httpLoggingInterceptor)
        verify(httpClientBuilder, times(1)).connectTimeout(AbstractGateway.OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        verify(httpClientBuilder, times(1)).readTimeout(AbstractGateway.OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        verify(httpClientBuilder, times(1)).writeTimeout(AbstractGateway.OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
        verify(retrofitClientBuilder, times(1)).client(okHttpClientMock)
        verify(retrofitClientBuilder, times(1)).baseUrl(urls.url)
        verify(retrofitClientBuilder, times(1)).addConverterFactory(any())
        verify(retrofitClientBuilder, times(1)).build()
    }

    @Test
    fun `when calling getRetrofit and the retrofit instance is created along with OKHttpClient without http log`() {
        // Given
        val abstractGateway = spy(AbstractGatewayClientMock(
            urls = urls,
            httpClientBuilder = httpClientBuilder,
            retrofitClientBuilder = retrofitClientBuilder,
            httpLoggingInterceptor = httpLoggingInterceptor,
            enableHttpLogging = false
        ))

        // When
        whenever(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)).doReturn(httpLoggingInterceptor)

        val retrofitLazy = abstractGateway.getRetrofit()

        // Then
        assertEquals(retrofitLazy.value, retrofitMock)

        // Verify
        verify(httpLoggingInterceptor, times(1)).setLevel(HttpLoggingInterceptor.Level.NONE)
        verify(httpClientBuilder, times(1)).addInterceptor(httpLoggingInterceptor)
        verify(httpClientBuilder, times(1)).connectTimeout(AbstractGateway.OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        verify(httpClientBuilder, times(1)).readTimeout(AbstractGateway.OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        verify(httpClientBuilder, times(1)).writeTimeout(AbstractGateway.OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
        verify(retrofitClientBuilder, times(1)).client(okHttpClientMock)
        verify(retrofitClientBuilder, times(1)).baseUrl(urls.url)
        verify(retrofitClientBuilder, times(1)).addConverterFactory(any())
        verify(retrofitClientBuilder, times(1)).build()
    }

}