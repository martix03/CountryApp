package it.prova.prima.spalla

import androidx.lifecycle.SavedStateHandle
import it.prova.prima.spalla.data.vo.Country
import it.prova.prima.spalla.data.vo.Language
import it.prova.prima.spalla.ui.main.MainFragment
import it.prova.prima.spalla.ui.main.MainViewModel
import org.junit.Test


class SearchUnitTest {

    private val mainFragment = MainFragment()

    @Test
    fun filterListOfRegion() {
        val list = listOf("Europe", "Africa", "Asia", "Americas", "Oceania")

        var listFiltered = mainFragment.filterRegion("Africa", list)
        assert(listFiltered.size == 1)

        listFiltered = mainFragment.filterRegion("A", list)
        assert(listFiltered.size == 3)
        assert(listFiltered.containsAll(listOf("Asia", "Africa", "Americas")))
        assert(!listFiltered.contains("Europe"))
    }

    @Test
    fun filterListOfLanguage() {
        val list = listOf(
            Language(name = "Italian"),
            Language(name = "German"),
            Language(name = "Spanish"),
            Language(name = "English"),
            Language(name = "Greek"),
            Language(name = "French")
        )

        var listFiltered = mainFragment.filterLanguage("Italian", list)
        assert(listFiltered.size == 1)

        listFiltered = mainFragment.filterLanguage("g", list)
        assert(listFiltered.size == 2)
        assert(listFiltered.containsAll(listOf("German", "Greek")))
        assert(!listFiltered.contains("French"))

    }

}