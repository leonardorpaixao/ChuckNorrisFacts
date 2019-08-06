package com.estudos.leonardo.chucknorrisfacts.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFact
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

internal class FactsByWordViewModel(val context: Context) : ViewModel() {
    private val api: ChuckNorrisFactsApi by lazy { ChuckNorrisFactsApi() }
    private val factByWord: MutableLiveData<ChuckNorrisFact> = MutableLiveData()
    private val noCategory: List<String> by lazy { listOf("  UNCATEGORIZED  ") }

    fun listenFact(): LiveData<ChuckNorrisFact> = factByWord

    fun getFactByWord(word: String) {
        api.requestFactByWord(word)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                when (it.total != 0) {
                    true -> it.result.map { factWeb ->

                        when (factWeb.categories == emptyList<String>()) {
                            true -> factByWord.value = ChuckNorrisFact(
                                category = noCategory,
                                url = factWeb.url,
                                icon_url = factWeb.url,
                                fact = factWeb.curiosity,
                                id = factWeb.id
                            )
                            false -> factByWord.value = ChuckNorrisFact(
                                category = factWeb.categories,
                                url = factWeb.url,
                                icon_url = factWeb.url,
                                fact = factWeb.curiosity,
                                id = factWeb.id
                            )
                        }
                    }
                    false -> Toast.makeText(context, "No results found. Try another word", Toast.LENGTH_LONG).show()
                }
            }, { e ->
                e.printStackTrace()
                Toast.makeText(context, "Erro: Sua oração foi fraca, tente novamente", Toast.LENGTH_LONG).show()
                Log.d("Erro: ", e.toString())
            })
    }
}
