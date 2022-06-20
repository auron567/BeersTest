package com.example.beerstest.presentation.beerdetail

import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.beerstest.R
import com.example.beerstest.core.base.BaseFragment
import com.example.beerstest.core.extensions.setSupportActionBar
import com.example.beerstest.core.extensions.supportActionBar
import com.example.beerstest.core.viewbinding.viewBinding
import com.example.beerstest.databinding.FragmentBeerDetailBinding
import com.example.beerstest.domain.model.BeerEntity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerDetailFragment :
    BaseFragment<BeerDetailContract.Event, BeerDetailContract.State, BeerDetailContract.Effect>() {

    override val binding by viewBinding(FragmentBeerDetailBinding::inflate)

    override val viewModel by viewModels<BeerDetailViewModel>()

    override fun setupUi() {
        setupToolbar()
        setupListeners()
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

    private fun setupListeners() {
        binding?.ivFavorite?.setOnClickListener {
            viewModel.launchEvent(BeerDetailContract.Event.OnFavoriteClicked)
        }
    }

    override fun handleState(state: BeerDetailContract.State) {
        renderBeer(state.currentBeer, state.isFavorite)
    }

    private fun renderBeer(beer: BeerEntity, isFavorite: Boolean) {
        binding?.apply {
            // Image
            ivBeer.load(beer.imageUrl) {
                placeholder(R.drawable.ic_beer)
                error(R.drawable.ic_beer)
            }

            // Name & year
            tvName.text = getString(R.string.template_name, beer.name, beer.productionYear)

            // Slogan
            tvSlogan.text = getString(R.string.template_slogan, beer.slogan)

            // Description
            tvDescription.text = beer.description

            // Favorite
            ivFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }
    }

    override fun handleEffect(effect: BeerDetailContract.Effect) {
        when (effect) {
            is BeerDetailContract.Effect.FavoriteSaved -> showSnackbar(R.string.favorite_saved)
            is BeerDetailContract.Effect.FavoriteRemoved -> showSnackbar(R.string.favorite_removed)
            is BeerDetailContract.Effect.Pop -> popStack()
        }
    }

    private fun showSnackbar(@StringRes message: Int) {
        binding?.let {
            Snackbar.make(it.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun popStack() {
        findNavController().popBackStack()
    }
}
