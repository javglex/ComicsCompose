package com.skymonkey.comicslibrary.model.api

import com.skymonkey.comicslibrary.BuildConfig
import com.skymonkey.comicslibrary.getHash
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Setup retrofit service for marvel api.
 */
object ApiService {
    private const val BASE_URL = "http://gateway.marvel.com/v1/public/"

    private fun getRetrofit(): Retrofit {
        val ts = System.currentTimeMillis().toString()
        val apiSecret = BuildConfig.MARVEL_SECRET
        val apiKey = BuildConfig.MARVEL_KEY
        val hash = getHash(ts, apiSecret, apiKey)

        // to intercept our request and then...
        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build()
            // add our url parameters
            request = request.newBuilder().url(url).build()
            // continue our request with updated parameters
            chain.proceed(request)
        }

        // add interceptor
        val client = OkHttpClient.Builder().addInterceptor(clientInterceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: MarvelApi = getRetrofit().create(MarvelApi::class.java)
}