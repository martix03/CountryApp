package it.prova.prima.spalla

import androidx.lifecycle.SavedStateHandle
import it.prova.prima.spalla.data.vo.Country
import it.prova.prima.spalla.data.vo.Language
import it.prova.prima.spalla.ui.main.MainFragment
import it.prova.prima.spalla.ui.main.MainViewModel
import org.junit.Test

import org.junit.Assert.*


class OrderUnitTest {

    private val mainViewModel = MainViewModel(SavedStateHandle())

    @Test
    fun filterCountiesRegion() {
        val list = listOf(
            Country(region = "Europe"),
            Country(region = "Africa"),
            Country(region = "Asia"),
            Country(region = "Asia"),
            Country(region = "Asia"),
            Country(region = "Americas"),
            Country(region = "Oceania")
        )

        val listOrdered = listOf("Africa", "Americas", "Asia", "Europe", "Oceania")

        val listFiltered = mainViewModel.filterResponseRegion(list)
        assert(listFiltered?.isNotEmpty() == true)
        assert(listFiltered?.count { it == "Asia" } == 1)
        assert(listFiltered?.equals(listOrdered) == true)
    }

    @Test
    fun filterCountiesLanguages() {
        val list = listOf(
            Country(languages = listOf(Language(name = "Italian"))),
            Country(languages = listOf(Language(name = "German"))),
            Country(languages = listOf(Language(name = "Spanish"))),
            Country(languages = listOf(Language(name = "English"))),
            Country(languages = listOf(Language(name = "Greek"))),
            Country(languages = listOf(Language(name = "Greek"))),
            Country(languages = listOf(Language(name = "Greek"))),
            Country(languages = listOf(Language(name = "French"))),
        )

        val listOrdered = listOf(
            Language(name = "English"),
            Language(name = "French"),
            Language(name = "German"),
            Language(name = "Greek"),
            Language(name = "Italian"),
            Language(name = "Spanish")
        )

        val listFiltered = mainViewModel.filterResponseLanguage(list)
        assert(listFiltered?.isNotEmpty() == true)
        assert(listFiltered?.count { it == Language(name = "Greek") } == 1)
        assert(listFiltered?.equals(listOrdered) == true)
    }
}