package com.example.beerstest.presentation.beerdetail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beerstest.R
import com.example.beerstest.core.base.BaseFragment
import com.example.beerstest.core.extensions.load
import com.example.beerstest.core.extensions.setSupportActionBar
import com.example.beerstest.core.extensions.supportActionBar
import com.example.beerstest.core.viewbinding.viewBinding
import com.example.beerstest.databinding.FragmentBeerDetailBinding
import com.example.beerstest.domain.model.BeerEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BeerDetailFragment :
    BaseFragment<BeerDetailContract.Event, BeerDetailContract.State, BeerDetailContract.Effect>() {

    @Inject
    lateinit var viewModelAssistedFactory: BeerDetailViewModel.Factory

    private val args: BeerDetailFragmentArgs by navArgs()

    override val binding by viewBinding(FragmentBeerDetailBinding::inflate)

    override val viewModel: BeerDetailViewModel by viewModels {
        BeerDetailViewModel.provideFactory(viewModelAssistedFactory, args.beer)
    }

    override fun setupUi() {
        setupToolbar()
    }

    private fun setupToolbar() {
        binding?.toolbar?.apply {
            // Set toolbar as action bar
            setSupportActionBar(this)

            // Set navigation button click
            setNavigationOnClickListener {
                viewModel.launchEvent(BeerDetailContract.Event.OnBackClicked)
            }
        }

        supportActionBar?.apply {
            // Setup action bar
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun handleState(state: BeerDetailContract.State) {
        renderBeer(state.beer)
    }

    private fun renderBeer(beer: BeerEntity) {
        binding?.apply {
            // Image
            ivBeer.load(beer.imageUrl)

            // Name & year
            tvName.text = getString(R.string.template_name, beer.name, beer.productionYear)

            // Slogan
            tvSlogan.text = getString(R.string.template_slogan, beer.slogan)

            // Description
            tvDescription.text = beer.description
        }
    }

    override fun handleEffect(effect: BeerDetailContract.Effect) {
        when (effect) {
            is BeerDetailContract.Effect.Pop -> popStack()
        }
    }

    private fun popStack() {
        findNavController().popBackStack()
    }
}
