package it.prova.prima.spalla.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.prova.prima.spalla.data.repository.CountryRepository
import it.prova.prima.spalla.data.vo.DetailCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class DetailCountryViewModel(private val state: SavedStateHandle) : ViewModel(), KoinComponent {
    private val repo: CountryRepository by inject { parametersOf(Dispatchers.IO) }

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    val detailOfCountry: MutableLiveData<DetailCountry> = state.getLiveData(DETAILCOUNTRY)

    fun getListOfCountries(code: String) {
        viewModelScope.launch {
            loading.value = true

            val stateDetail = state.get<DetailCountry>(DETAILCOUNTRY)
            if (stateDetail == null) {
                state.set(DETAILCOUNTRY, repo.getDetail(code))
            }

            loading.value = false
        }
    }

    companion object {
        const val DETAILCOUNTRY = "DETAILCOUNTRY"
    }
}