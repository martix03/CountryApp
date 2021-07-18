package it.prova.prima.spalla.di

import it.prova.prima.spalla.data.api.CountryService
import org.koin.dsl.module
import retrofit2.Retrofit


val serviceModule = module {
    single { get<Retrofit>().create(CountryService::class.java) }
}