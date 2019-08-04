package com.estudos.leonardo.chucknorrisfacts.controller


import com.estudos.leonardo.chucknorrisfacts.domain.model.Categories
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.service.ChuckNorrisFactsApiDef
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class ChuckNorrisFactsApi {


    private val service: ChuckNorrisFactsApiDef

    //inicialização do Retrofit
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

        //definição do objeto da interface para conduzir pesquisa dos dados solicitados.
        service = retrofit.create<ChuckNorrisFactsApiDef>(ChuckNorrisFactsApiDef::class.java)
    }

    //retorna um fato aleatório no formato da classe de negocio
    fun requestFact(): Observable<ChuckNorrisFacts> {
        return service.getRandomChuckNorrisFact()
            .map { factWeb ->
                if (factWeb.category == null) {
                    ChuckNorrisFacts(
                        listOf("  UNCATEGORIZED  "), factWeb.icon_url,
                        factWeb.id, factWeb.url, factWeb.curiosity
                    )
                } else {
                    ChuckNorrisFacts(
                        factWeb.category, factWeb.icon_url,
                        factWeb.id, factWeb.url, factWeb.curiosity
                    )
                }


            }
    }

    //retorna um fato de acordo com a categoria fornecida
    fun requestFactByCategory(requestCategoty: String): Observable<ChuckNorrisFacts> {
        return service.getFactByCategory(requestCategoty)
            .map { factWeb ->
                if (factWeb.category == null) {
                    ChuckNorrisFacts(
                        listOf("  UNCATEGORIZED  "), factWeb.icon_url,
                        factWeb.id, factWeb.url, factWeb.curiosity
                    )
                } else {
                    ChuckNorrisFacts(
                        factWeb.category, factWeb.icon_url,
                        factWeb.id, factWeb.url, factWeb.curiosity
                    )
                }
            }
    }

    //retorna uma lista das categorias
    fun requestCategories(): Observable<Categories> {
        return service.getCategories()
            .map { categoriesWeb ->
                Categories(categoriesWeb)
            }
    }

    //retorna uma lista de fatos de acordo com a palavra fornecida.
    fun requestFactByWord(query: String): Observable<ChuckNorrisFacts>? {
        return service.getFactByWord(query)
            .flatMap { factResult ->
                Observable.from(factResult.result)
                    .map { factWeb ->
                        if (factWeb.category == null) {
                            ChuckNorrisFacts(
                                listOf("  UNCATEGORIZED  "), factWeb.icon_url,
                                factWeb.id, factWeb.url, factWeb.curiosity
                            )
                        } else {
                            ChuckNorrisFacts(
                                factWeb.category, factWeb.icon_url,
                                factWeb.id, factWeb.url, factWeb.curiosity
                            )
                        }
                    }
            }

    }
}
