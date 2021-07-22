package it.prova.prima.spalla.ui.main

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import it.prova.prima.spalla.R
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            binding?.containerSearch?.visibility =
                if (binding?.containerSearch?.isVisible == true) View.GONE else View.VISIBLE
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListOfCountries()
        binding?.recycler?.adapter = controller

        setHasOptionsMenu(true)

        observe()
    }

    private fun observe() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding?.loader?.visibility = if (it) View.VISIBLE else View.GONE
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