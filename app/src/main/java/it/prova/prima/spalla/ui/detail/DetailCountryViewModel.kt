package it.prova.prima.spalla.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.prova.prima.spalla.data.repository.CountryRepository
import it.prova.prima.spalla.data.vo.DetailCountry
import it.prova.prima.spalla.httpTryCatch
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
            httpTryCatch(
                onSuccess = {
                    val stateDetail = state.get<DetailCountry>(DETAILCOUNTRY)
                    if (stateDetail == null) {
                        val response = repo.getDetail(code)
                        if (response.isSuccessful)
                            state.set(DETAILCOUNTRY, response.body())
                        else
                            error.value = response.errorBody()?.string()
                    }
                },
                onError = {
                    error.value = it
                })
            loading.value = false
        }
    }

    companion object {
        const val DETAILCOUNTRY = "DETAILCOUNTRY"
    }
}