package com.example.coinapp.components

import android.content.Context
import android.util.AttributeSet
import android.widget.SearchView
import com.example.coinapp.ui.FilterAdapter

class SearchViewFilterable(context: Context, attributeSet: AttributeSet) :
    SearchView(context, attributeSet) {

    fun setOnQueryTextListener(listAdapter: FilterAdapter) {
        setOnQueryTextListener(OnQueryTextListenerFilter(this, listAdapter))
    }

    class OnQueryTextListenerFilter(
        private val searchView: SearchView,
        private val adapter: FilterAdapter
    ) :
        OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            adapter.filterData(query)
            searchView.clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            adapter.filterData(newText)
            return true
        }
    }

}