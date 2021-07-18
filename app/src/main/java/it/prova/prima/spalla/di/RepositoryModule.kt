package it.prova.prima.spalla.di

import it.prova.prima.spalla.data.repository.CountryRepository
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.dsl.module


val repositoryModule = module {
    single { (dispatcher: CoroutineDispatcher) ->
        CountryRepository(dispatcher)
    }
}