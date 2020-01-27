package com.estudos.leonardo.chucknorrisfacts.di

import android.app.Application
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.data.FactsInfraStructure
import com.estudos.leonardo.chucknorrisfacts.domain.service.FactsService
import com.estudos.leonardo.chucknorrisfacts.view.fact_by_category.FactsByCategoryViewModel
import com.estudos.leonardo.chucknorrisfacts.view.fact_by_word.FactByWordViewModel
import com.estudos.leonardo.chucknorrisfacts.view.random_fact.RandomFactsViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class FactsApplication : Application(), KodeinAware {

    private val appModule = Kodein.Module(name = "application") {
        bind() from provider {
            this@FactsApplication as Application
        }
        bind<FactsService>() with provider {
            FactsInfraStructure()
        }
        bind() from provider { ChuckNorrisFactAdapter(applicationContext) }

        bind() from provider { RandomFactsViewModel(factsService = instance()) }

        bind() from provider { FactsByCategoryViewModel(factsService = instance()) }

        bind() from provider { FactByWordViewModel(factsService = instance()) }

    }

    override val kodein = ConfigurableKodein(mutable = true).apply {
        addImport(appModule)
    }

}
