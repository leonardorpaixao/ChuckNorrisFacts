package com.estudos.leonardo.chucknorrisfacts.Controller


import com.estudos.leonardo.chucknorrisfacts.Model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.Model.ChuckNorrisFactsApiDef
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.lang.reflect.Field

class ChuckNorrisFactsApi {

    val service: ChuckNorrisFactsApiDef

    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()

        service = retrofit.create<ChuckNorrisFactsApiDef>(ChuckNorrisFactsApiDef::class.java)


    }

    //retornar fatos no formato da classe de negocio
    fun loadFact(): Observable<ChuckNorrisFacts>? {
        return service.returnChuckNorrisFact()
            .map { factWeb ->
                if (factWeb.category.isEmpty()) {
                    ChuckNorrisFacts(listOf("UNCATEGORIZED"), factWeb.icon_url, factWeb.id, factWeb.url, factWeb.curiosity)
                } else {
                    ChuckNorrisFacts(factWeb.category, factWeb.icon_url, factWeb.id, factWeb.url, factWeb.curiosity)
                }
            }


    }
}