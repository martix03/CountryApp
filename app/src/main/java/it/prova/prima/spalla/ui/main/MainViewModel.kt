package it.prova.prima.spalla.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.prova.prima.spalla.data.repository.CountryRepository
import it.prova.prima.spalla.data.vo.Country
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

    fun getListOfCountries() {
        viewModelScope.launch {
            loading.value = true
            val stateList = state.get<List<Country>>(LISTCOUNTRIES)
            if (stateList == null) {
                state.set(LISTCOUNTRIES, repo.getCountries())
            }
            loading.value = false
        }
    }

    fun searchForRegion(string: String) {
        viewModelScope.launch {
            loading.value = true

//            val response = repo.searchForRegion(region)

            listOfCountries.value = repo.getCountries()

            loading.value = false
        }
    }

    companion object {
        const val LISTCOUNTRIES = "LISTCOUNTRIES"
    }
}