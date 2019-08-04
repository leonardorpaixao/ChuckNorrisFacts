package com.estudos.leonardo.chucknorrisfacts.domain.service

import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFactsResult
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChucknorrisFactsWeb
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ChuckNorrisFactsApiDef {
    @GET("random")
    fun getRandomChuckNorrisFact(): Observable<ChucknorrisFactsWeb>

    @GET("random")
    fun getFactByCategory(@Query("category") category: String): Observable<ChucknorrisFactsWeb>

    @GET("categories")
    fun getCategories(): Observable<List<String>>

    @GET("search")
    fun getFactByWord(@Query("query") query: String): Observable<ChuckNorrisFactsResult>

}
