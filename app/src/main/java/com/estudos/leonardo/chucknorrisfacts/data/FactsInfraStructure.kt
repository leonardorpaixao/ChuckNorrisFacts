package com.estudos.leonardo.chucknorrisfacts.data


import com.estudos.leonardo.chucknorrisfacts.domain.model.Categories
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.service.FactsService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class FactsInfraStructure : FactsService {

    private val api: OpenFactsGateway

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

        api = retrofit.create<OpenFactsGateway>(OpenFactsGateway::class.java)

    }

    override fun getCategories(): Observable<Categories> {
        return this.api.categories()
            .map {
                Categories(it)
            }
    }

    override fun getRandomFact(): Observable<ChuckNorrisFacts> {
        return api.randomFact()
            .map { factWeb ->

                val category = when (factWeb.factsCategories.isNullOrEmpty()) {
                    true -> listOf("  UNCATEGORIZED  ")
                    false -> factWeb.factsCategories
                }
                ChuckNorrisFacts(
                    category = category,
                    iconUrl = factWeb.factIconUrl,
                    id = factWeb.factId,
                    url = factWeb.factUrl,
                    fact = factWeb.factCuriosity
                )
            }
    }

    override fun getFactByCategory(requestCategoty: String): Observable<ChuckNorrisFacts> {
        return api.factByCategory(requestCategoty)
            .map { factWeb ->

                val category = when (factWeb.factsCategories.isNullOrEmpty()) {
                    true -> listOf("  UNCATEGORIZED  ")
                    false -> factWeb.factsCategories
                }
                ChuckNorrisFacts(
                    category = category,
                    iconUrl = factWeb.factIconUrl,
                    id = factWeb.factId,
                    url = factWeb.factUrl,
                    fact = factWeb.factCuriosity
                )
            }
    }

    override fun getFactByWord(query: String): Observable<ChuckNorrisFacts>? {
        return api.factByWord(query)
            .flatMap {
                Observable.from(it.factsList)
                    .map { factWeb ->

                        val category = when (factWeb.factsCategories.isNullOrEmpty()) {
                            true -> listOf("  UNCATEGORIZED  ")
                            false -> factWeb.factsCategories
                        }
                        ChuckNorrisFacts(
                            category = category,
                            iconUrl = factWeb.factIconUrl,
                            id = factWeb.factId,
                            url = factWeb.factUrl,
                            fact = factWeb.factCuriosity
                        )
                    }
            }
    }
}
