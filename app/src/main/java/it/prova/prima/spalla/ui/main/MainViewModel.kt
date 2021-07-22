package it.prova.prima.spalla.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.prova.prima.spalla.data.repository.CountryRepository
import it.prova.prima.spalla.data.vo.Country
import it.prova.prima.spalla.data.vo.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class MainViewModel(private val state: SavedStateHandle) : ViewModel(), KoinComponent {
    private val repo: CountryRepository by inject { parametersOf(Dispatchers.IO) }

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val listOfCountries: MutableLiveData<List<Country>> = state.getLiveData(LISTCOUNTRIES)
    val listOfRegions: MutableLiveData<List<String>> = state.getLiveData(LISTREGIONS)
    val listOfLanguages: MutableLiveData<List<Language>> = state.getLiveData(LISTLANGUAGES)

    fun getListOfCountries(refresh: Boolean = false) {
        viewModelScope.launch {
            loading.value = true
            val stateList = state.get<List<Country>>(LISTCOUNTRIES)
            if (stateList == null || refresh) {
                val response = repo.getCountries()
                state.apply {
                    set(LISTCOUNTRIES, response)
                    set(
                        LISTREGIONS,
                        response.map { it.region }.distinct().filter { !it.isNullOrEmpty() }
                            .sortedBy { it })
                    set(
                        LISTLANGUAGES,
                        response.filter { it.languages != null }.flatMap { it.languages!! }
                            .distinct().sortedBy { it.name })
                }

            }
            loading.value = false
        }
    }

    fun searchForRegion(region: String) {
        viewModelScope.launch {
            loading.value = true

            val response = repo.searchForRegion(region.lowercase())
                .map { Country(it.alpha2Code, it.name, it.region, it.capital, it.languages) }
            state.set(LISTCOUNTRIES, response)

            loading.value = false
        }
    }

    fun searchForLanguage(language: String) {
        viewModelScope.launch {
            loading.value = true

            val response = repo.searchForLanguage(language)
                .map { Country(it.alpha2Code, it.name, it.region, it.capital, it.languages) }
            state.set(LISTCOUNTRIES, response)

            loading.value = false
        }
    }

    companion object {
        const val LISTCOUNTRIES = "LISTCOUNTRIES"
        const val LISTREGIONS = "LISTREGIONS"
        const val LISTLANGUAGES = "LISTLANGUAGES"
    }
}