package com.example.beerstest.presentation.beerlist.adapter

import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationListener(
    private val loadMoreItems: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var isScrolling = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        (recyclerView.layoutManager as GridLayoutManager?)?.let { manager ->
            val currentItems = manager.childCount
            val totalItems = manager.itemCount
            val scrollOutItems = manager.findFirstVisibleItemPosition()

            if (isScrolling && (currentItems + scrollOutItems) == totalItems) {
                isScrolling = false
                loadMoreItems()
            }
        }
    }
}
