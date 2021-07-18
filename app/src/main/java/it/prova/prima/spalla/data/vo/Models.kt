package it.prova.prima.spalla.data.vo

import com.google.gson.annotations.SerializedName


data class Country(
    @SerializedName("alpha2Code")
    val alpha2Code: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?
)
