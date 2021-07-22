package it.prova.prima.spalla.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import it.prova.prima.spalla.R
import it.prova.prima.spalla.databinding.DialogSearchPopupBinding
import it.prova.prima.spalla.ui.main.controller.search.SearchController

class SearchPopup(
    private val labelTitle: String,
    private val hintText: String,
    private val onTextChange: (String) -> List<String>,
    private val onClick: (String) -> Unit
) : DialogFragment() {

    private var binding: DialogSearchPopupBinding? = null

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, SearchPopup::class.java.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogSearchPopupBinding.inflate(inflater).let {
        binding = it
        it.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = SearchController {
            onClick(it)
            this@SearchPopup.dismiss()
        }

        binding?.adviceList?.adapter = controller
        binding?.title?.text = labelTitle
        binding?.search?.apply {
            hint = hintText
            controller.setData(onTextChange(""))
            addTextChangedListener {
                it?.toString()?.let { s ->
                    val newList = onTextChange(s)
                    controller.setData(newList)
                }

            }
        }
    }


}