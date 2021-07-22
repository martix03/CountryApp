package it.prova.prima.spalla.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import it.prova.prima.spalla.R
import it.prova.prima.spalla.databinding.DetailFragmentBinding
import okhttp3.internal.filterList

class DetailCountryFragment : Fragment() {
    private var binding: DetailFragmentBinding? = null
    private val viewModel: DetailCountryViewModel by viewModels()
    private val args: DetailCountryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DetailFragmentBinding.inflate(inflater).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListOfCountries(args.code)

        observe()
    }

    @SuppressLint("SetTextI18n")
    private fun observe() {
        viewModel.detailOfCountry.observe(viewLifecycleOwner) {
            binding?.countryName?.text = it.name
            binding?.countryRegion?.text = "${it.region} - ${it.subregion}"

            binding?.countryNameComplete?.rowTitle?.text = getString(R.string.native_name)
            binding?.countryNameComplete?.rowValue?.text = it.nativeName

            binding?.countryLanguage?.rowTitle?.text = getString(R.string.country_language)
            binding?.countryLanguage?.rowValue?.text = it.languages?.filterList { it.name != null }
                ?.joinToString(separator = " - ") { it.name ?: "" }

            binding?.countrySurface?.rowTitle?.text = getString(R.string.country_surface)
            binding?.countrySurface?.rowValue?.text = "${it.area} km²"

            binding?.countryPopulation?.rowTitle?.text = getString(R.string.country_population)
            binding?.countryPopulation?.rowValue?.text = it.population?.toString()

            if (it.borders?.isEmpty() == true)
                binding?.countryBorders?.root?.visibility = View.GONE
            binding?.countryBorders?.rowTitle?.text = getString(R.string.country_borders)
            binding?.countryBorders?.rowValue?.text =
                it.borders?.joinToString(separator = " - ") { it }

            binding?.countryTimezones?.rowTitle?.text = getString(R.string.country_timezones)
            binding?.countryTimezones?.rowValue?.text =
                it.timezones?.joinToString(separator = " - ") { it }

            binding?.countryCurrencies?.rowTitle?.text = getString(R.string.country_currencies)
            binding?.countryCurrencies?.rowValue?.text =
                it.currencies?.joinToString(separator = " - ") { "${it.name} (${it.code})" }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding?.loader?.visibility = if (it) View.VISIBLE else View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}