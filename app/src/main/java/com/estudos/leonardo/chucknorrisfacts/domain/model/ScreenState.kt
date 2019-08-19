package com.estudos.leonardo.chucknorrisfacts.domain.model

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    data class Error(val error: Throwable) : ScreenState<Nothing>()
    data class Result<out T>(val result: T) : ScreenState<T>()
    object Failed : ScreenState<Nothing>()
}