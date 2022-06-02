package com.example.beerstest.presentation.beerlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.domain.model.BeerItem

class BeerDiffCallback : DiffUtil.ItemCallback<BeerItem>() {

    override fun areItemsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
        return when {
            oldItem is BeerEntity && newItem is BeerEntity -> oldItem.id == newItem.id
            else -> oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
        return oldItem == newItem
    }
}
