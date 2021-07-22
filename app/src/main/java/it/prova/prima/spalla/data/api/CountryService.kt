package it.prova.prima.spalla.data.api

import it.prova.prima.spalla.data.vo.Country
import it.prova.prima.spalla.data.vo.DetailCountry
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {

    @GET("all?fields=alpha2Code;name;region;capital;languages;")
    suspend fun getCountries(): List<Country>

    @GET("alpha/{code}")
    suspend fun getDetail(@Path("code") code: String): DetailCountry

    @GET("region/{region}")
    suspend fun searchForRegion(@Path("region") region: String): List<DetailCountry>

    @GET("lang/{language}")
    suspend fun searchForLanguage(@Path("language") language: String): List<DetailCountry>

}