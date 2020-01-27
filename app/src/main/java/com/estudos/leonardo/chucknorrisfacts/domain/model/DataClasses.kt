package com.estudos.leonardo.chucknorrisfacts.domain.model

data class ChuckNorrisFacts(
    val category: List<String>?,
    val iconUrl: String,
    val id: String,
    val url: String,
    var fact: String
)

data class Categories( val categories : List<String>)



