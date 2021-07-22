package it.prova.prima.spalla.ui.main

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import it.prova.prima.spalla.R
import it.prova.prima.spalla.data.vo.Language
import it.prova.prima.spalla.databinding.MainFragmentBinding
import it.prova.prima.spalla.ui.main.controller.country.CountryController

class MainFragment : Fragment() {
    private var binding: MainFragmentBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private var listOfRegion = listOf<String>()
    private var listOfLanguage = listOf<Language>()
    private var isSwitchChecked = false

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
            if (binding?.containerSearch?.isVisible == true) {
                binding?.containerSearch?.visibility = View.GONE
                viewModel.getListOfCountries(true)
                binding?.switchCompat?.isChecked = false
            } else binding?.containerSearch?.visibility = View.VISIBLE
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListOfCountries()
        binding?.recycler?.adapter = controller



        binding?.searchRegion?.setOnClickListener {
            search().show(requireActivity().supportFragmentManager)
        }

        binding?.searchLanguage?.setOnClickListener {
            search().show(requireActivity().supportFragmentManager)
        }

        binding?.switchCompat?.setOnCheckedChangeListener { _, isChecked ->
            viewModel.getListOfCountries(true)
            isSwitchChecked = isChecked
            binding?.searchLanguage?.visibility = if (isChecked) View.VISIBLE else View.GONE
            binding?.searchRegion?.visibility = if (!isChecked) View.VISIBLE else View.GONE
        }

        setHasOptionsMenu(true)

        observe()
    }

    private fun search() = SearchPopup(
        labelTitle = getString(if (isSwitchChecked) R.string.language_select else R.string.region_select),
        hintText = getString(if (isSwitchChecked) R.string.hint_language else R.string.hint_region),
        onTextChange = { s ->
            if (isSwitchChecked)
                listOfLanguage.filter { it.name?.startsWith(s, ignoreCase = true) == true }
                    .mapNotNull { it.name }
            else
                listOfRegion.filter { it.startsWith(s, ignoreCase = true) }


        },
        onClick = {
            if (isSwitchChecked) {
                binding?.searchLanguage?.text = it
                listOfLanguage.find { lang -> lang.name == it }?.iso6391?.let {
                    viewModel.searchForLanguage(it)
                }
            } else {
                binding?.searchRegion?.text = it
                viewModel.searchForRegion(it)
            }
        }
    )

    private fun observe() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding?.loader?.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {

        }

        viewModel.listOfCountries.observe(viewLifecycleOwner) {
            controller.setData(it)
        }

        viewModel.listOfRegions.observe(viewLifecycleOwner) {
            listOfRegion = it
        }

        viewModel.listOfLanguages.observe(viewLifecycleOwner) {
            listOfLanguage = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}