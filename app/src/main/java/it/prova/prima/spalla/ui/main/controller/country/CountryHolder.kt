package it.prova.prima.spalla.ui.main.controller.country

import androidx.recyclerview.widget.RecyclerView
import it.prova.prima.spalla.data.api.getFlagUrl
import it.prova.prima.spalla.data.vo.Country
import it.prova.prima.spalla.databinding.RowcountryBinding
import it.prova.prima.spalla.load

typealias CountryClickAction = (Country) -> Unit

class CountryHolder(private val itemBinding: RowcountryBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(country: Country, countryClick: CountryClickAction) {

        itemBinding.countryName.text = country.name
        itemBinding.countryRegion.text = country.region

        country.alpha2Code?.let {
            itemBinding.countryFlag.load(getFlagUrl(it))
        }

        itemBinding.root.setOnClickListener {
            countryClick.invoke(country)
        }

    }
}