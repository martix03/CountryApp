package it.prova.prima.spalla.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import it.prova.prima.spalla.R
import it.prova.prima.spalla.data.api.getFlagUrl
import it.prova.prima.spalla.databinding.BottomSheetDetailDialogFragmentBinding
import it.prova.prima.spalla.load


class BottomSheetDetailDialogFragment : BottomSheetDialogFragment() {
    private var binding: BottomSheetDetailDialogFragmentBinding? = null
    private val args: BottomSheetDetailDialogFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = BottomSheetDetailDialogFragmentBinding.inflate(inflater).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.detail.apply {
            binding?.countryName?.text = name
            binding?.countryRegion?.text = getString(R.string.region, region)
            binding?.countryCapital?.text = getString(R.string.capital, capital)

            alpha2Code?.let { code ->

                binding?.countryFlag?.load(getFlagUrl(code))
                binding?.root?.setOnClickListener {
                    findNavController().navigate(BottomSheetDetailDialogFragmentDirections
                        .actionBottomSheetDetailDialogFragmentToDetailCountryFragment(code))
                }

            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}