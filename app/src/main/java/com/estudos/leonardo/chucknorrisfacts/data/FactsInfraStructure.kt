package com.estudos.leonardo.chucknorrisfacts.data


import com.estudos.leonardo.chucknorrisfacts.domain.model.Categories
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.service.FactsService
import rx.Observable

class FactsInfraStructure(
    val api: OpenFactsGateway
) : FactsService {

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
