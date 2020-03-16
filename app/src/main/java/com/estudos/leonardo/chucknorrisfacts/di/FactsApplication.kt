package com.estudos.leonardo.chucknorrisfacts.di

import android.app.Application
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactAdapter
import com.estudos.leonardo.chucknorrisfacts.data.FactsInfraStructure
import com.estudos.leonardo.chucknorrisfacts.data.OpenFactsGateway
import com.estudos.leonardo.chucknorrisfacts.data.RetrofitBuilder
import com.estudos.leonardo.chucknorrisfacts.view.fact_by_category.FactsByCategoryViewModel
import com.estudos.leonardo.chucknorrisfacts.view.fact_by_word.FactByWordViewModel
import com.estudos.leonardo.chucknorrisfacts.view.random_fact.RandomFactsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit


class FactsApplication : Application(), KodeinAware {

    private val appModule = Kodein.Module(name = "application") {
        bind() from provider {
            this@FactsApplication as Application
        }
        bind() from provider { ChuckNorrisFactAdapter(applicationContext) }


        bind() from provider { RandomFactsViewModel(factsService = instance()) }

        bind() from provider { FactsByCategoryViewModel(factsService = instance()) }

        bind() from provider { FactByWordViewModel(factsService = instance()) }

        bind<OkHttpClient>() with singleton {
            OkHttpClient.Builder()
                .apply {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
                .build()
        }

        bind<Retrofit>() with singleton {
            RetrofitBuilder(
                okHttpClient = instance()
            )
        }

        bind<OpenFactsGateway>() with singleton {
            val retrofit: Retrofit = instance()
            retrofit.create(OpenFactsGateway::class.java)
        }

        bind<FactsInfraStructure>() with provider {
            FactsInfraStructure(
                api = instance()
            )
        }
    }

    override val kodein = ConfigurableKodein(mutable = true).apply {
        addImport(appModule)
    }

}
