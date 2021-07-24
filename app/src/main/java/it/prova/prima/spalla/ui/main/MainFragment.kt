package it.prova.prima.spalla.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
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
            viewModel.saveSearchBar(binding?.containerSearch?.isVisible == false)
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
            if (binding?.switchCompat?.isPressed == true) {
                viewModel.saveSwitchState(isChecked)
                viewModel.saveSearchStringState(null)
                viewModel.getListOfCountries(true)
                searchVisibility(isChecked)
            }
        }

        setHasOptionsMenu(true)

        observe()
    }

    fun filterLanguage(language: String, list: List<Language>): List<String> =
        list.filter { it.name?.startsWith(language, ignoreCase = true) == true }
            .mapNotNull { it.name }

    fun filterRegion(region: String, list: List<String>): List<String> =
        list.filter { it.startsWith(region, ignoreCase = true) }

    private fun search() = SearchPopup(
        labelTitle = getString(if (isSwitchChecked) R.string.language_select else R.string.region_select),
        hintText = getString(if (isSwitchChecked) R.string.hint_language else R.string.hint_region),
        onTextChange = { s ->
            if (isSwitchChecked)
                filterLanguage(s, listOfLanguage)
            else
                filterRegion(s, listOfRegion)
        },
        onClick = {
            viewModel.saveSearchStringState(it)
            if (isSwitchChecked) {
                listOfLanguage.find { lang -> lang.name == it }?.iso6391?.let {
                    viewModel.searchForLanguage(it)
                }
            } else {
                viewModel.searchForRegion(it)
            }
        }
    )

    private fun observe() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding?.loader?.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
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

        viewModel.showSearchBar.observe(viewLifecycleOwner) {
            isSwitchChecked = it.switchState ?: false

            if (it.show) {
                binding?.containerSearch?.visibility = View.VISIBLE
                binding?.switchCompat?.isChecked = isSwitchChecked
                binding?.searchLanguage?.text = if (isSwitchChecked) it.searchString else null
                binding?.searchRegion?.text = if (!isSwitchChecked) it.searchString else null
                searchVisibility(isSwitchChecked)
            } else {
                binding?.containerSearch?.visibility = View.GONE
                viewModel.getListOfCountries(true)
            }
        }
    }

    private fun searchVisibility(isCheckedSwitch: Boolean) {
        binding?.searchRegion?.visibility = if (!isCheckedSwitch) View.VISIBLE else View.GONE
        binding?.searchLanguage?.visibility = if (isCheckedSwitch) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}