package com.example.beerstest.presentation.beerlist

import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beerstest.R
import com.example.beerstest.core.base.BaseFragment
import com.example.beerstest.core.extensions.setSupportActionBar
import com.example.beerstest.core.extensions.supportActionBar
import com.example.beerstest.core.viewbinding.viewBinding
import com.example.beerstest.databinding.FragmentBeerListBinding
import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.presentation.beerlist.adapter.BeerAdapter
import com.example.beerstest.presentation.beerlist.adapter.PaginationListener
import com.example.beerstest.presentation.beersearch.BeerSearchBottomSheet
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerListFragment :
    BaseFragment<BeerListContract.Event, BeerListContract.State, BeerListContract.Effect>() {

    override val binding by viewBinding(FragmentBeerListBinding::inflate)

    override val viewModel by viewModels<BeerListViewModel>()

    private val beerAdapter = BeerAdapter(::onBeerClicked)

    private val paginationListener = PaginationListener(::onLoadMoreBeers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(BeerSearchBottomSheet.REQUEST_KEY) { _, bundle ->
            handleFragmentResult(bundle)
        }
    }

    override fun setupUi() {
        setupToolbar()
        setupBeerRecyclerView()
        setupSearchButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.title = getString(R.string.beers)
    }

    private fun setupBeerRecyclerView() {
        binding?.rvBeer?.apply {
            // Set layout manager
            val layoutManager = GridLayoutManager(requireContext(), BEER_RECYCLER_VIEW_SPAN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = beerAdapter.getSpanSize(position)
                }
            }
            this.layoutManager = layoutManager

            // Set adapter
            adapter = beerAdapter

            // Set pagination
            addOnScrollListener(paginationListener)
        }
    }

    private fun setupSearchButton() {
        binding?.btnSearch?.setOnClickListener {
            viewModel.launchEvent(BeerListContract.Event.OnSearchClicked)
        }
    }

    override fun handleState(state: BeerListContract.State) {
        binding?.apply {
            // Beer list
            rvBeer.isInvisible = state.showEmptyView
            beerAdapter.submitList(state.beers)

            // Empty view
            tvNoBeers.isVisible = state.showEmptyView
        }
    }

    override fun handleEffect(effect: BeerListContract.Effect) {
        when (effect) {
            is BeerListContract.Effect.GoToBeerDetail -> goToBeerDetail(effect.beer)
            is BeerListContract.Effect.GoToBeerSearch -> goToBeerSearch()
            is BeerListContract.Effect.ShowErrorSnackbar -> showErrorSnackBar()
        }
    }

    private fun onBeerClicked(beer: BeerEntity) {
        viewModel.launchEvent(BeerListContract.Event.OnBeerClicked(beer))
    }

    private fun onLoadMoreBeers() {
        viewModel.launchEvent(BeerListContract.Event.OnLoadMoreBeers)
    }

    private fun handleFragmentResult(bundle: Bundle) {
        bundle.getString(BeerSearchBottomSheet.NAME_KEY)?.let { beerName ->
            viewModel.launchEvent(BeerListContract.Event.OnBeerSearched(beerName))
        }
    }

    private fun goToBeerDetail(beer: BeerEntity) {
        val direction = BeerListFragmentDirections.actionBeerListFragmentToBeerDetailFragment(beer)
        findNavController().navigate(direction)
    }

    private fun goToBeerSearch() {
        val direction = BeerListFragmentDirections.actionBeerListFragmentToBeerSearchBottomSheet()
        findNavController().navigate(direction)
    }

    private fun showErrorSnackBar() {
        binding?.let {
            Snackbar.make(it.root, R.string.error_occurred, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showLoader() {
        beerAdapter.showLoadingItem()
    }

    override fun hideLoader() {
        beerAdapter.hideLoadingItem()
    }
}

private const val BEER_RECYCLER_VIEW_SPAN_COUNT = 2
