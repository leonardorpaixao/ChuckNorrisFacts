package com.estudos.leonardo.chucknorrisfacts.di

import android.app.Application
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.view.RandomFactsViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider


class FactsApplication : Application(), KodeinAware {

    private val appModule = Kodein.Module(name = "application") {
            bind() from provider {
                this@FactsApplication as Application
            }

        bind() from provider {RandomFactsViewModel()}

        bind() from provider {ChuckNorrisFactAdapter(applicationContext)}
        }

        override val kodein = ConfigurableKodein(mutable = true).apply {
            addImport(appModule)
        }

}
