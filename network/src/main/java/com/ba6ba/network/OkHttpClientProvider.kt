package com.ba6ba.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

interface OKHttpClientProvider : Provider<OkHttpClient.Builder>

class DefaultOKHttpClientProvider @Inject constructor() : OKHttpClientProvider {

    private companion object {
        const val TIMEOUT = 30
    }

    override fun get(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
    }
}
