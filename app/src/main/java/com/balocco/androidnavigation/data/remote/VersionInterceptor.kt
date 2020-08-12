package com.balocco.androidnavigation.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val VERSION_PARAM_KEY = "v"

class VersionInterceptor(
    private val supportedVersion: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(VERSION_PARAM_KEY, supportedVersion)
            .build()

        val requestBuilder = original.newBuilder().url(newUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}