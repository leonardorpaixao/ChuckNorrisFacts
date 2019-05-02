package com.estudos.leonardo.chucknorrisfacts.Model

import retrofit2.http.GET
import rx.Observable

interface ChuckNorrisFactsApiDef {
    @GET ("random")
    fun returnChuckNorrisFact() : Observable<ChucknorrisFactsWebResult>
}