package com.estudos.leonardo.chucknorrisfacts.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface ChuckNorrisFactsApiDef {
    @GET("random")
    fun getRandomChuckNorrisFact(): Observable<ChucknorrisFactsWeb>

    @GET("random")
    fun getFactFromCategory(@Query("category") category: String): Observable<ChucknorrisFactsWeb>

    @GET("categories")
    fun getCategories() : Observable<List<String>>

    /*fun returnFactFromCategory(@Query("category") category : String): Observable<ChucknorrisFactsWeb>*/
/*    @GET ("random")*/
}
