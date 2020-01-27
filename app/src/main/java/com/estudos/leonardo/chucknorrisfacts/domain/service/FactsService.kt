package com.estudos.leonardo.chucknorrisfacts.domain.service

import com.estudos.leonardo.chucknorrisfacts.domain.model.Categories
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import rx.Observable

interface FactsService {

    fun getRandomFact(): Observable<ChuckNorrisFacts>

    fun getFactByCategory(requestCategoty: String): Observable<ChuckNorrisFacts>

    fun getCategories(): Observable<Categories>

    fun getFactByWord(query: String): Observable<ChuckNorrisFacts>?
}