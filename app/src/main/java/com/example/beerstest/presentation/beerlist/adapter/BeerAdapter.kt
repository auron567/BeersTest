package com.example.beerstest.presentation.beerlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beerstest.core.extensions.load
import com.example.beerstest.databinding.ItemBeerBinding
import com.example.beerstest.databinding.ItemLoaderBinding
import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.domain.model.BeerItem
import com.example.beerstest.domain.model.BeerLoader

class BeerAdapter(
    private val onBeerClicked: (beer: BeerEntity) -> Unit
) : ListAdapter<BeerItem, RecyclerView.ViewHolder>(BeerDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BeerEntity -> VIEW_TYPE_BEER_ENTITY
            is BeerLoader -> VIEW_TYPE_BEER_LOADER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BEER_ENTITY -> BeerEntityViewHolder.from(parent)
            VIEW_TYPE_BEER_LOADER -> BeerLoaderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beerItem = getItem(position)
        if (holder is BeerEntityViewHolder) {
            holder.bind(beerItem as BeerEntity, onBeerClicked)
        }
    }

    fun getSpanSize(position: Int): Int {
        return when (val viewType = getItemViewType(position)) {
            VIEW_TYPE_BEER_ENTITY -> 1
            VIEW_TYPE_BEER_LOADER -> 2
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun showLoadingItem() {
        val newList = currentList.toMutableList()
        newList.add(BeerLoader)
        submitList(newList)
    }

    fun hideLoadingItem() {
        if (currentList.isNotEmpty() && currentList.last() is BeerLoader) {
            val newList = currentList.toMutableList()
            newList.removeLast()
            submitList(newList)
        }
    }

    class BeerEntityViewHolder(private val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): BeerEntityViewHolder {
                val binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BeerEntityViewHolder(binding)
            }
        }

        fun bind(
            beer: BeerEntity,
            onBeerClicked: (beer: BeerEntity) -> Unit
        ) {
            binding.apply {
                // Set click
                root.setOnClickListener {
                    onBeerClicked(beer)
                }

                // Set image & name
                ivBeer.load(beer.imageUrl)
                tvName.text = beer.name
            }
        }
    }

    class BeerLoaderViewHolder(binding: ItemLoaderBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): BeerLoaderViewHolder {
                val binding = ItemLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BeerLoaderViewHolder(binding)
            }
        }
    }
}

private const val VIEW_TYPE_BEER_ENTITY = 0
private const val VIEW_TYPE_BEER_LOADER = 1
