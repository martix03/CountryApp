package it.prova.prima.spalla.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import it.prova.prima.spalla.databinding.MainFragmentBinding
import it.prova.prima.spalla.ui.main.controller.CountryController

class MainFragment : Fragment() {
    private var binding: MainFragmentBinding? = null
    private val viewModel: MainViewModel by viewModels()

    private val controller by lazy {
        CountryController {

            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToBottomSheetDetailDialogFragment(
                    it
                )
            )

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MainFragmentBinding.inflate(inflater).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListOfCountries()
        binding?.recycler?.adapter = controller

        observe()
    }

    private fun observe() {
        viewModel.loading.observe(viewLifecycleOwner) {

        }

        viewModel.error.observe(viewLifecycleOwner) {

        }

        viewModel.listOfCountries.observe(viewLifecycleOwner) {
            controller.setData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}