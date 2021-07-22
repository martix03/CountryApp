package it.prova.prima.spalla.data.repository

import it.prova.prima.spalla.data.api.CountryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CountryRepository(private val dispatcher: CoroutineDispatcher) : KoinComponent {

    private val service: CountryService by inject()

    suspend fun getCountries() = withContext(dispatcher) {
        service.getCountries()
    }

    suspend fun getDetail(code: String) = withContext(dispatcher) {
        service.getDetail(code)
    }

    suspend fun searchForRegion(region: String) = withContext(dispatcher) {
        service.searchForRegion(region)
    }

}