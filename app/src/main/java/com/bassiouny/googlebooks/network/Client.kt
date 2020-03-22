package com.bassiouny.googlebooks.network

import com.bassiouny.googlebooks.utils.API_KEY
import com.bassiouny.googlebooks.utils.BASE_URL
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class Client {
    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private val REQUEST_TIMEOUT = 60

    fun getService(): BooksApi {
        return getClient().create(BooksApi::class.java)
    }

    private fun getClient(): Retrofit {

        initOkHttp()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", API_KEY)
                .build()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}