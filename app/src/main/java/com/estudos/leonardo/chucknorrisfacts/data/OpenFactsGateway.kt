package com.estudos.leonardo.chucknorrisfacts.data

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface OpenFactsGateway {
    @GET("random")
    fun randomFact(): Observable<ChucknorrisFactsWeb>

    @GET("random")
    fun factByCategory(@Query("category") category: String): Observable<ChucknorrisFactsWeb>

    @GET("categories")
    fun categories(): Observable<List<String>>

    @GET("search")
    fun factByWord(@Query("query") query: String): Observable<ChuckNorrisFactsResult>

}
