package com.estudos.leonardo.chucknorrisfacts.Model

import com.google.gson.annotations.SerializedName

data class ChucknorrisFactsWebResult(val result: List<ChucknorrisFactsWeb>)

data class ChucknorrisFactsWeb( val category: String,
                                val icon_url: String,
                                val id: String,
                                val url: String,
                                @SerializedName("value")
                                var curiosity: String)




