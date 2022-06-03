package com.example.beerstest.presentation.beersearch

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.beerstest.core.base.BaseBottomSheet
import com.example.beerstest.core.viewbinding.viewBinding
import com.example.beerstest.databinding.BottomSheetBeerSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerSearchBottomSheet :
    BaseBottomSheet<BeerSearchContract.Event, BeerSearchContract.State, BeerSearchContract.Effect>() {

    override val binding by viewBinding(BottomSheetBeerSearchBinding::inflate)

    override val viewModel by viewModels<BeerSearchViewModel>()

    override fun setupUi() {
        setupListeners()
    }

    private fun setupListeners() {
        binding?.apply {
            etName.addTextChangedListener { name ->
                viewModel.launchEvent(BeerSearchContract.Event.OnNameChanged(name.toString()))
            }

            btnSearch.setOnClickListener {
                viewModel.launchEvent(BeerSearchContract.Event.OnSearchClicked)
            }
        }
    }

    override fun handleState(state: BeerSearchContract.State) {
        binding?.btnSearch?.isEnabled = state.isButtonEnabled
    }

    override fun handleEffect(effect: BeerSearchContract.Effect) {
        when (effect) {
            is BeerSearchContract.Effect.BackToBeerList -> backToBeerList(effect.beerName)
        }
    }

    private fun backToBeerList(beerName: String) {
        dismiss()
    }
}
