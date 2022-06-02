package com.example.beerstest.presentation.beersearch

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
    }

    override fun handleState(state: BeerSearchContract.State) {
    }

    override fun handleEffect(effect: BeerSearchContract.Effect) {
    }
}
