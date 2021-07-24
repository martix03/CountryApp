package it.prova.prima.spalla.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.prova.prima.spalla.data.repository.CountryRepository
import it.prova.prima.spalla.data.vo.Country
import it.prova.prima.spalla.data.vo.Language
import it.prova.prima.spalla.data.vo.StateOfSearch
import it.prova.prima.spalla.httpTryCatch
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
    val showSearchBar: MutableLiveData<StateOfSearch> = state.getLiveData(SHOWSEARCHBAR)

    fun filterResponseRegion(list: List<Country>?): List<String?>? =
        list?.map { it.region }?.distinct()
            ?.filter { !it.isNullOrEmpty() }?.sortedBy { it }

    fun filterResponseLanguage(list: List<Country>?): List<Language>? =
        list?.filter { it.languages != null }
            ?.flatMap { it.languages!! }?.distinct()
            ?.sortedBy { it.name }

    fun getListOfCountries(refresh: Boolean = false) {
        viewModelScope.launch {
            loading.value = true
            val stateList = state.get<List<Country>>(LISTCOUNTRIES)
            if (stateList == null || refresh) {
                httpTryCatch(
                    onSuccess = {
                        val response = repo.getCountries()
                        if (response.isSuccessful) {
                            state.apply {
                                set(LISTCOUNTRIES, response.body())
                                set(
                                    LISTREGIONS,
                                    filterResponseRegion(response.body())
                                )
                                set(
                                    LISTLANGUAGES,
                                    filterResponseLanguage(response.body())
                                )
                            }
                        } else {
                            error.value = response.errorBody()?.string()
                        }
                    },
                    onError = {
                        error.value = it
                    })
            }
            loading.value = false
        }
    }

    fun searchForRegion(region: String) {
        viewModelScope.launch {
            loading.value = true
            httpTryCatch(
                onSuccess = {
                    val response = repo.searchForRegion(region.lowercase())
                    if (response.isSuccessful) {
                        val listRegions = response.body()
                            ?.map {
                                Country(
                                    it.alpha2Code,
                                    it.name,
                                    it.region,
                                    it.capital,
                                    it.languages
                                )
                            }
                        state.set(LISTCOUNTRIES, listRegions)
                    } else {
                        error.value = response.errorBody()?.string()
                    }
                },
                onError = {
                    error.value = it
                })


            loading.value = false
        }
    }

    fun searchForLanguage(language: String) {
        viewModelScope.launch {
            loading.value = true
            httpTryCatch(
                onSuccess = {
                    val response = repo.searchForLanguage(language)
                    if (response.isSuccessful) {
                        val listLanguages = response.body()
                            ?.map {
                                Country(
                                    it.alpha2Code,
                                    it.name,
                                    it.region,
                                    it.capital,
                                    it.languages
                                )
                            }
                        state.set(LISTCOUNTRIES, listLanguages)
                    } else {
                        error.value = response.errorBody()?.string()
                    }
                },
                onError = {
                    error.value = it
                })

            loading.value = false
        }
    }

    fun saveSearchBar(show: Boolean) {
        state.set(SHOWSEARCHBAR, StateOfSearch(show))
    }

    fun saveSwitchState(isChecked: Boolean) {
        state.set(SHOWSEARCHBAR, state.get<StateOfSearch>(SHOWSEARCHBAR)?.apply {
            switchState = isChecked
        })
    }

    fun saveSearchStringState(search: String?) {
        state.set(SHOWSEARCHBAR, state.get<StateOfSearch>(SHOWSEARCHBAR)?.apply {
            searchString = search
        })
    }


    companion object {
        const val LISTCOUNTRIES = "LISTCOUNTRIES"
        const val LISTREGIONS = "LISTREGIONS"
        const val LISTLANGUAGES = "LISTLANGUAGES"
        const val SHOWSEARCHBAR = "SHOWSEARCHBAR"
    }
}