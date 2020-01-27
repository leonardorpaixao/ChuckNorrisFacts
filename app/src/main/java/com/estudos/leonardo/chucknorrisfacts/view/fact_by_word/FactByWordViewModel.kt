package com.estudos.leonardo.chucknorrisfacts.view.fact_by_word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.estudos.leonardo.chucknorrisfacts.common.ext.setSubscriber
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState
import com.estudos.leonardo.chucknorrisfacts.domain.service.FactsService

internal class FactByWordViewModel(
    private val factsService: FactsService
) {

    private val factByWordScreenState = MutableLiveData<ScreenState<ChuckNorrisFacts>>()

    private fun setScreenState(screenState: ScreenState<ChuckNorrisFacts>) {
        factByWordScreenState.value = screenState
    }

    fun listenResult(): LiveData<ScreenState<ChuckNorrisFacts>> = factByWordScreenState

    fun getFactByWord(wantedWord: String) {
        setScreenState(ScreenState.Loading)
        factsService.getFactByWord(wantedWord)
            ?.setSubscriber()
            ?.subscribe({
                setScreenState(ScreenState.Result(it))
            }, { error ->
                error.printStackTrace()
                setScreenState(ScreenState.Error(error))
            })
    }
}