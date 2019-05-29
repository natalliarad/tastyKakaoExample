package com.natallia.radaman.tastysearchkakaoexample.favourites

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.natallia.radaman.tastysearchkakaoexample.R
import com.natallia.radaman.tastysearchkakaoexample.common.ChildActivity
import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import com.natallia.radaman.tastysearchkakaoexample.common.RecipeAdapter
import com.natallia.radaman.tastysearchkakaoexample.recipe.recipeIntent
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepositoryImpl
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_noresults.*

class FavouritesActivity : ChildActivity(), FavouritesPresenter.View {

    private val presenter by lazy {
        FavouritesPresenter(RecipeRepositoryImpl.getRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        presenter.attachView(this)

        presenter.loadFavourites()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    // region FavoritesPresenter.View methods
    override fun showEmptyRecipes() {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.VISIBLE
        noresultsTitle.text = getString(R.string.nofavorites)
    }

    override fun showRecipes(recipes: List<Recipe>) {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.GONE

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = RecipeAdapter(recipes, object : RecipeAdapter.Listener {
            override fun onClickItem(item: Recipe) {
                startActivity(recipeIntent(item.sourceUrl))
            }

            override fun onAddFavourite(item: Recipe) {
                // no-op
            }

            override fun onRemoveFavourite(item: Recipe) {
                presenter.removeFavourite(item)
            }
        })
    }

    override fun refreshRemovedFavourite(recipeIndex: Int) {
        list.adapter?.let {
            (it as RecipeAdapter).removeItem(recipeIndex)
            it.notifyItemRemoved(recipeIndex)
            if (it.itemCount == 0) {
                showEmptyRecipes()
            }
        }
    }
    // endregion
}
