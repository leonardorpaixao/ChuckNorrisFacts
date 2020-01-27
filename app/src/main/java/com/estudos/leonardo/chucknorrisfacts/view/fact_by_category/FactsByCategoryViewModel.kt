package com.estudos.leonardo.chucknorrisfacts.view.fact_by_category


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.leonardo.chucknorrisfacts.common.ext.setSubscriber
import com.estudos.leonardo.chucknorrisfacts.domain.model.ChuckNorrisFacts
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenState
import com.estudos.leonardo.chucknorrisfacts.domain.service.FactsService

internal class FactsByCategoryViewModel(
    private val factsService: FactsService
) : ViewModel() {

    fun getCategoriesList() {
        setCategoriesScreenState(ScreenState.Loading)
        factsService.getCategories()
            .setSubscriber()
            ?.subscribe({
                setCategoriesScreenState(ScreenState.Result(it.categories))
            },
                {
                    setCategoriesScreenState(ScreenState.Error(it))
                })

    }

    private val screenState: MutableLiveData<ScreenState<ChuckNorrisFacts>> = MutableLiveData()

    fun listenFact(): LiveData<ScreenState<ChuckNorrisFacts>> = screenState

    private fun setScreenState(screenState: ScreenState<ChuckNorrisFacts>) {
        this.screenState.value = screenState
    }

    private val categoriesScreenState: MutableLiveData<ScreenState<List<String>>> =
        MutableLiveData()

    fun listenCategories(): LiveData<ScreenState<List<String>>> = categoriesScreenState

    private fun setCategoriesScreenState(screenState: ScreenState<List<String>>) {
        this.categoriesScreenState.value = screenState
    }

    fun getFactByCategory(category: String) {
        setScreenState(ScreenState.Loading)
        factsService.getFactByCategory(category)
            .setSubscriber()
            ?.subscribe({ fact ->
                setScreenState(ScreenState.Result(fact))
            },
                {
                    setScreenState(ScreenState.Error(it))
                })
    }
}