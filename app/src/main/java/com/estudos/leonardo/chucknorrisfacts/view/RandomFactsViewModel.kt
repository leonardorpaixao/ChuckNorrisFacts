package com.estudos.leonardo.chucknorrisfacts.view

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFact
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RandomFactsViewModel(val context: Context) : ViewModel() {
    val api: ChuckNorrisFactsApi by lazy { ChuckNorrisFactsApi() }
    private val randomFact: MutableLiveData<ChuckNorrisFact> = MutableLiveData()

    fun listenFact(): LiveData<ChuckNorrisFact> = randomFact

    fun getRandomFact(){
        api.requestFact()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                randomFact.value = it
            }, { e ->
                e.printStackTrace()
                Toast.makeText(
                    context, "Erro: Sua oração foi fraca, tente novamente",
                    Toast.LENGTH_LONG
                ).show()

            })
    }

}