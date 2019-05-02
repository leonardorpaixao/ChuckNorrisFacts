package com.estudos.leonardo.chucknorrisfacts.Controller


import com.estudos.leonardo.chucknorrisfacts.Model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.Model.ChuckNorrisFactsApiDef
import com.estudos.leonardo.chucknorrisfacts.Model.ChucknorrisFactsWeb
import com.estudos.leonardo.chucknorrisfacts.Model.ChucknorrisFactsWebResult
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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

    fun loadFact(): Observable<ChuckNorrisFacts>?{
        return service.returnChuckNorrisFact()
            .flatMap { ChucknorrisFactsWebResult -> Observable.from(ChucknorrisFactsWebResult.result)}
            .map { factWeb -> ChuckNorrisFacts(factWeb.id, factWeb.category, factWeb.icon_url, factWeb.url, factWeb.curiosity )
               /* if(factWeb.result.category == "null"){
                    ChuckNorrisFacts(factWeb.result.id, "UNCATEGORIZED", factWeb.result.icon_url, factWeb.result.url, factWeb.result.curiosity )
                }else{
                    ChuckNorrisFacts(factWeb.result.id, factWeb.result.category, factWeb.result.icon_url, factWeb.result.url, factWeb.result.curiosity )
                }*/
            }



    }
}