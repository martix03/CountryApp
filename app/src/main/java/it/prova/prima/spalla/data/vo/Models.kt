package it.prova.prima.spalla.data.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Country(
    @SerializedName("alpha2Code")
    val alpha2Code: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("capital")
    val capital: String?
): Parcelable

data class DetailCountry(
    @SerializedName("alpha2Code")
    val alpha2Code: String?,
    @SerializedName("alpha3Code")
    val alpha3Code: String?,
    @SerializedName("altSpellings")
    val altSpellings: List<String>?,
    @SerializedName("area")
    val area: Int?,
    @SerializedName("borders")
    val borders: List<String>?,
    @SerializedName("callingCodes")
    val callingCodes: List<String>?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("cioc")
    val cioc: String?,
    @SerializedName("currencies")
    val currencies: List<Currency>?,
    @SerializedName("demonym")
    val demonym: String?,
    @SerializedName("flag")
    val flag: String?,
    @SerializedName("gini")
    val gini: Double?,
    @SerializedName("languages")
    val languages: List<Language>?,
    @SerializedName("latlng")
    val latlng: List<BigDecimal>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nativeName")
    val nativeName: String?,
    @SerializedName("numericCode")
    val numericCode: String?,
    @SerializedName("population")
    val population: Int?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("regionalBlocs")
    val regionalBlocs: List<RegionalBloc>?,
    @SerializedName("subregion")
    val subregion: String?,
    @SerializedName("timezones")
    val timezones: List<String>?,
    @SerializedName("topLevelDomain")
    val topLevelDomain: List<String>?,
    @SerializedName("translations")
    val translations: Map<String, String>?
)

data class Currency(
    @SerializedName("code")
    val code: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?
)

data class Language(
    @SerializedName("iso639_1")
    val iso6391: String?,
    @SerializedName("iso639_2")
    val iso6392: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nativeName")
    val nativeName: String?
)

data class RegionalBloc(
    @SerializedName("acronym")
    val acronym: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("otherAcronyms")
    val otherAcronyms: List<String>?,
    @SerializedName("otherNames")
    val otherNames: List<String>?
)

