package com.estudos.leonardo.chucknorrisfacts.Model

import com.google.gson.annotations.SerializedName

data class ChucknorrisFactsWeb( val category: List<String>?,
                                val icon_url: String,
                                val id: String,
                                val url: String,
                                @SerializedName("value")
                                var curiosity: String)




