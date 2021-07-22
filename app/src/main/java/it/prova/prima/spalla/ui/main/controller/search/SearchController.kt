package it.prova.prima.spalla.ui.main.controller.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.prova.prima.spalla.databinding.RowsearchBinding


class SearchController(
    private val searchClick: SearchClickAction
) : RecyclerView.Adapter<SearchHolder>() {

    private var searchList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val itemBinding =
            RowsearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val search: String = searchList[position]
        holder.bind(search, searchClick)
    }

    override fun getItemCount(): Int = searchList.size

    fun setData(data: List<String>) {
        searchList = data
        notifyDataSetChanged()
    }
}