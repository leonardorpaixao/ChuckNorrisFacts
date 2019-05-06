package com.estudos.leonardo.chucknorrisfacts.controller


import com.estudos.leonardo.chucknorrisfacts.model.Categories
import com.estudos.leonardo.chucknorrisfacts.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.model.ChuckNorrisFactsApiDef
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

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

    //retornar um fato aleatório no formato da classe de negocio
    fun requestFact(): Observable<ChuckNorrisFacts> {
        return service.getRandomChuckNorrisFact()/*FactFromCategory("science")*/
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

    fun requestFactByCategory(requestCategoty: String): Observable<ChuckNorrisFacts> {
        return service.getFactFromCategory(requestCategoty)
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

    fun requestCategories(): Observable<Categories> {
        return service.getCategories()
            .map { categoriesWeb ->
                Categories(categoriesWeb)
            }
    }

    /*fun loadMoviesFull(): Observable<ChuckNorrisFacts> {
        return service.listMovies()
            .flatMap { filmResults -> Observable.from(filmResults.results) }
            .flatMap { film ->
                val movieObj = Movie(film.title, film.episodeId, ArrayList<Character>())
                Observable.zip(
                    Observable.just(movieObj),
                    Observable.from(film.personUrls)
                        .flatMap { personUrl ->
                            Observable.concat(
                                getCache(personUrl),
                                service.loadPerson(Uri.parse(personUrl).lastPathSegment)
                                    .doOnNext { person ->
                                        peopleCache.put(personUrl, person)
                                    }
                            ).first()
                        }
                        .map { person ->
                            Character(person!!.name, person.gender)
                        }.toList(),
                    { movie, characters ->
                        movie.characters.addAll(characters)
                        movie
                    })
            }
    }*/
}
