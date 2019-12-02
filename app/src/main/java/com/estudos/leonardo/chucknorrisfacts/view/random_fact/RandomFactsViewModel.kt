package com.estudos.leonardo.chucknorrisfacts.view.random_fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState
import com.estudos.leonardo.chucknorrisfacts.domain.service.FactsService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RandomFactsViewModel(
    private val factsService: FactsService
) : ViewModel() {
    private val screenState: MutableLiveData<ScreenState<ChuckNorrisFacts>> = MutableLiveData()

    fun listenFact(): LiveData<ScreenState<ChuckNorrisFacts>> = screenState

    fun getRandomFact() {
        screenState.value = ScreenState.Loading
        factsService.getRandomFact()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                screenState.value = ScreenState.Result(it)
            }, { e ->
                e.printStackTrace()
                screenState.value = ScreenState.Error(e)

            })
    }

}