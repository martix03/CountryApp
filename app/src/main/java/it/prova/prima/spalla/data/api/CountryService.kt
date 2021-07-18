package it.prova.prima.spalla.data.api

import it.prova.prima.spalla.data.vo.Country
import retrofit2.http.GET

interface CountryService {

    @GET("all?fields=alpha2Code;name;region;")
    suspend fun getCountries(): List<Country>

}