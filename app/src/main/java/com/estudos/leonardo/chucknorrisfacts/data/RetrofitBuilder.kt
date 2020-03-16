package com.estudos.leonardo.chucknorrisfacts.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    operator fun invoke(
        apiUrl: String = "https://api.chucknorris.io/jokes/",
        okHttpClient: OkHttpClient
    ): Retrofit =
        with(Retrofit.Builder()) {
            baseUrl(apiUrl)
            client(okHttpClient)
            addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson))
            build()
        }

    private val gson = GsonBuilder().setLenient().create()


}