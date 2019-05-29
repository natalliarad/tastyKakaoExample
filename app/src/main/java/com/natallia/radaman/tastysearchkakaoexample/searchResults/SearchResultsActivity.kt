package com.natallia.radaman.tastysearchkakaoexample.searchResults

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.natallia.radaman.tastysearchkakaoexample.TastySearchKakaoApp
import com.natallia.radaman.tastysearchkakaoexample.R
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepositoryImpl
import com.natallia.radaman.tastysearchkakaoexample.common.ChildActivity
import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import com.natallia.radaman.tastysearchkakaoexample.common.RecipeAdapter
import com.natallia.radaman.tastysearchkakaoexample.recipe.recipeIntent
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_noresults.*

fun Context.searchResultsIntent(query: String): Intent {
    return Intent(this, SearchResultsActivity::class.java).apply {
        putExtra(EXTRA_QUERY, query)
    }
}

private const val EXTRA_QUERY = "EXTRA_QUERY"

class SearchResultsActivity : ChildActivity(), SearchResultsPresenter.View {

    private val presenter by lazy {
        (application as TastySearchKakaoApp).getSearchResultsPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val query = intent.getStringExtra(EXTRA_QUERY)
        supportActionBar?.subtitle = query

        presenter.attachView(this)

        presenter.search(query)
        retry.setOnClickListener { presenter.search(query) }
    }

    // region SearchResultsPresenter.View methods
    override fun showEmptyRecipes() {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.VISIBLE
    }

    override fun showRecipes(recipes: List<Recipe>) {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.GONE

        setupRecipeList(recipes)
    }

    override fun showLoading() {
        loadingContainer.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
        list.visibility = View.GONE
        noresultsContainer.visibility = View.GONE
    }

    override fun showError() {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        list.visibility = View.GONE
        noresultsContainer.visibility = View.GONE
    }

    override fun refreshFavoriteStatus(recipeIndex: Int) {
        list.adapter?.notifyItemChanged(recipeIndex)
    }
    // endregion

    private fun setupRecipeList(recipes: List<Recipe>) {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = RecipeAdapter(recipes, object : RecipeAdapter.Listener {
            override fun onClickItem(recipe: Recipe) {
                startActivity(recipeIntent(recipe.sourceUrl))
            }

            override fun onAddFavourite(recipe: Recipe) {
                // 1
                presenter.addFavorite(recipe)
            }

            override fun onRemoveFavourite(recipe: Recipe) {
                // 2
                presenter.removeFavorite(recipe)
            }
        })
    }
}
