package com.balocco.androidnavigation.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val CLIENT_ID_PARAM_KEY = "client_id"
private const val CLIENT_ID_PARAM_VALUE = "XXA3AYHR5OFZSLU0EM2RFXOTHXGZECIRRH1NF2YDDC13TS4F"
private const val CLIENT_SECRET_PARAM_KEY = "client_secret"
private const val CLIENT_SECRET_PARAM_VALUE = "ATQW5X0NSRPNTVIKMX40QHV545A5IDW41RGG4LFHF0VDMOZI"

class AuthInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(CLIENT_ID_PARAM_KEY, CLIENT_ID_PARAM_VALUE)
            .addQueryParameter(CLIENT_SECRET_PARAM_KEY, CLIENT_SECRET_PARAM_VALUE)
            .build()

        val requestBuilder = original.newBuilder().url(newUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}