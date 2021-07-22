package it.prova.prima.spalla.ui.main.controller.search

import androidx.recyclerview.widget.RecyclerView
import it.prova.prima.spalla.databinding.RowsearchBinding

typealias SearchClickAction = (String) -> Unit

class SearchHolder(private val itemBinding: RowsearchBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(search: String, searchClick: SearchClickAction) {

        itemBinding.rowTitle.text = search

        itemBinding.root.setOnClickListener {
            searchClick.invoke(search)
        }

    }
}