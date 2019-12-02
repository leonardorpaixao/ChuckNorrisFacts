package com.estudos.leonardo.chucknorrisfacts.data

import com.google.gson.annotations.SerializedName


data class ChuckNorrisFactsResult(
    @SerializedName ("result") val factsList: List<ChucknorrisFactsWeb>)

data class ChucknorrisFactsWeb(
    @SerializedName("factsCategories") val factsCategories: List<String>,
    @SerializedName("icon_url") val factIconUrl: String,
    @SerializedName("id") val factId: String,
    @SerializedName("url") val factUrl: String,
    @SerializedName("value") var factCuriosity: String
)




