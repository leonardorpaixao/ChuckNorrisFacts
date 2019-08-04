package com.estudos.leonardo.chucknorrisfacts.domain.model

import com.google.gson.annotations.SerializedName

data class ChuckNorrisFactsResult(val result: List<ChucknorrisFactsWeb>)

data class ChucknorrisFactsWeb(val categories: List<String>?,
                               val icon_url: String,
                               val id: String,
                               val url: String,
                               @SerializedName("value")
                                var curiosity: String)

data class CategoriesWeb(val categories : List<String>)




