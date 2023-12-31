package com.akshaymayekar.movielistingapp.ui.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

open abstract class PaginationListener(private val layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {

    /**
     * Supporting only LinearLayoutManager for now.
     */


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean


    companion object {
        const val PAGE_START = 1

        /**
         * Set scrolling threshold here (for now i'm assuming 10 item in one page)
         */
        private const val PAGE_SIZE = 20
    }
}