package com.natallia.radaman.tastysearchkakaoexample.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.natallia.radaman.tastysearchkakaoexample.TastySearchKakaoApp
import com.natallia.radaman.tastysearchkakaoexample.R
import com.natallia.radaman.tastysearchkakaoexample.common.ChildActivity
import com.natallia.radaman.tastysearchkakaoexample.recentIngredients.RecentIngredientsActivity
import com.natallia.radaman.tastysearchkakaoexample.recentIngredients.recentIngredientsIntent
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepositoryImpl
import com.natallia.radaman.tastysearchkakaoexample.searchResults.searchResultsIntent
import kotlinx.android.synthetic.main.activity_search.*

private const val SELECT_INGREDIENTS_REQUEST = 322

class SearchActivity : ChildActivity(), SearchPresenter.View {

    private val presenter by lazy {
        (application as TastySearchKakaoApp).getSearchPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter.attachView(this)

        searchButton.setOnClickListener {
            val query = ingredients.text.toString()
            presenter.search(query)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadRecentIngredients()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_INGREDIENTS_REQUEST && resultCode == RESULT_OK) {
            data?.let {
                val selected = it.getStringArrayListExtra(
                    RecentIngredientsActivity.EXTRA_INGREDIENTS_SELECTED
                )
                ingredients.setText(selected.joinToString(","))
            }
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    // region SearchPresenter.View methods
    override fun showQueryRequiredMessage() {
        // Hide keyboard
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        Snackbar
            .make(searchButton, getString(R.string.search_query_required), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showSearchResults(query: String) {
        startActivity(searchResultsIntent(query))
    }

    override fun hideRecentIngredients() {
        recentButton.visibility = View.GONE
    }

    override fun showRecentIngredients() {
        recentButton.visibility = View.VISIBLE
        recentButton.setOnClickListener {
            startActivityForResult(recentIngredientsIntent(), SELECT_INGREDIENTS_REQUEST)
        }
    }
    // endregion
}
