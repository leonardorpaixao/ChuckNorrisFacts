package com.estudos.leonardo.chucknorrisfacts.view.dashboad


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenSelected
import com.estudos.leonardo.chucknorrisfacts.domain.model.ScreenSelected.*

class DashBoardViewModel(): ViewModel(){

    private val screenSelected: MutableLiveData<ScreenSelected> = MutableLiveData()


    fun listenScreenSelected(): LiveData<ScreenSelected> = screenSelected

    fun setScreen(screen: ScreenSelected){
        when (screen) {
            FactByWord -> screenSelected.value = screen
            FactByCategory -> screenSelected.value = screen
            FactByRandom -> screenSelected.value = screen
        }
    }
}