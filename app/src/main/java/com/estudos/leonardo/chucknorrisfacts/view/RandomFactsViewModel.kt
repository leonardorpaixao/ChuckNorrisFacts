package com.estudos.leonardo.chucknorrisfacts.view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.leonardo.chucknorrisfacts.controller.ChuckNorrisFactsApi
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RandomFactsViewModel : ViewModel() {
    val api: ChuckNorrisFactsApi by lazy { ChuckNorrisFactsApi() }
    private val randomFact: MutableLiveData<ScreenState<ChuckNorrisFacts>> = MutableLiveData()

    fun listenFact(): LiveData<ScreenState<ChuckNorrisFacts>> = randomFact

    fun getRandomFact(){
        randomFact.value = ScreenState.Loading
        api.requestFact()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                randomFact.value = ScreenState.Result(it)
            }, { e ->
                e.printStackTrace()
                randomFact.value = ScreenState.Error(e)

            })
    }

}