package it.prova.prima.spalla.ui.main.controller.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.prova.prima.spalla.data.vo.Country
import it.prova.prima.spalla.databinding.RowcountryBinding


class CountryController(
    private val countryClick: CountryClickAction
) : RecyclerView.Adapter<CountryHolder>() {

    private var countryList = listOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val itemBinding =
            RowcountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val country: Country = countryList[position]
        holder.bind(country, countryClick)
    }

    override fun getItemCount(): Int = countryList.size

    fun setData(data: List<Country>) {
        countryList = data
        notifyDataSetChanged()
    }
}