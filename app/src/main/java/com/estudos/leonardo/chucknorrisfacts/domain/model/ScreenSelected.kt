package com.estudos.leonardo.chucknorrisfacts.domain.model

sealed class ScreenSelected{
    object FactByWord: ScreenSelected()
    object FactByRandom: ScreenSelected()
    object FactByCategory: ScreenSelected()
}