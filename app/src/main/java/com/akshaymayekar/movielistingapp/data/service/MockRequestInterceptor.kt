package com.akshaymayekar.movielistingapp.data.service

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockRequestInterceptor(private val context: Context) : Interceptor {

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaTypeOrNull()
        private const val MOCK = "mock"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val context1 = context.applicationContext
        val request = chain.request()

        val header = request.header(MOCK)

        if (header != null) {
            val filename = request.url.pathSegments.last()
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .code(200)
                .body(
                    context.readFileFromAssets("mocks/$filename.json")
                        .toResponseBody(JSON_MEDIA_TYPE)
                )
                .build()
        }

        return chain.proceed(request.newBuilder().removeHeader(MOCK).build())
    }

    private fun Context.readFileFromAssets(filePath: String): String {
        return resources.assets.open(filePath).bufferedReader().use {
            it.readText()
        }
    }
}