package com.himanshu.aifinancialassistant.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val apiKey: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-goog-api-key", apiKey)
             .build()
        return chain.proceed(request)
    }
}