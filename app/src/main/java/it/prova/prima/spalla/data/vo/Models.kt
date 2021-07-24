package it.prova.prima.spalla.data.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Country(
    @SerializedName("alpha2Code")
    val alpha2Code: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("capital")
    val capital: String? = null,
    @SerializedName("languages")
    val languages: List<Language>? = null
) : Parcelable

@Parcelize
data class DetailCountry(
    @SerializedName("alpha2Code")
    val alpha2Code: String? = null,
    @SerializedName("alpha3Code")
    val alpha3Code: String? = null,
    @SerializedName("altSpellings")
    val altSpellings: List<String>? = null,
    @SerializedName("area")
    val area: Double? = null,
    @SerializedName("borders")
    val borders: List<String>? = null,
    @SerializedName("callingCodes")
    val callingCodes: List<String>? = null,
    @SerializedName("capital")
    val capital: String? = null,
    @SerializedName("cioc")
    val cioc: String? = null,
    @SerializedName("currencies")
    val currencies: List<Currency>? = null,
    @SerializedName("demonym")
    val demonym: String? = null,
    @SerializedName("flag")
    val flag: String? = null,
    @SerializedName("gini")
    val gini: Double? = null,
    @SerializedName("languages")
    val languages: List<Language>? = null,
    @SerializedName("latlng")
    val latlng: List<BigDecimal>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("nativeName")
    val nativeName: String? = null,
    @SerializedName("numericCode")
    val numericCode: String? = null,
    @SerializedName("population")
    val population: Int? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("regionalBlocs")
    val regionalBlocs: List<RegionalBloc>? = null,
    @SerializedName("subregion")
    val subregion: String? = null,
    @SerializedName("timezones")
    val timezones: List<String>? = null,
    @SerializedName("topLevelDomain")
    val topLevelDomain: List<String>? = null,
    @SerializedName("translations")
    val translations: Map<String, String>? = null
) : Parcelable

@Parcelize
data class Currency(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("symbol")
    val symbol: String? = null
) : Parcelable

@Parcelize
data class Language(
    @SerializedName("iso639_1")
    val iso6391: String? = null,
    @SerializedName("iso639_2")
    val iso6392: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("nativeName")
    val nativeName: String? = null
) : Parcelable

@Parcelize
data class RegionalBloc(
    @SerializedName("acronym")
    val acronym: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("otherAcronyms")
    val otherAcronyms: List<String>? = null,
    @SerializedName("otherNames")
    val otherNames: List<String>? = null
) : Parcelable

@Parcelize
data class StateOfSearch(
    val show: Boolean = false,
    var searchString: String? = null,
    var switchState: Boolean? = null
) : Parcelable
