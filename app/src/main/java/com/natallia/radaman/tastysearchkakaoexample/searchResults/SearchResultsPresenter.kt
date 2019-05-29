package com.natallia.radaman.tastysearchkakaoexample.searchResults

import com.natallia.radaman.tastysearchkakaoexample.common.BasePresenter
import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository
import com.natallia.radaman.tastysearchkakaoexample.repository.RepositoryCallback

class SearchResultsPresenter(private val repository: RecipeRepository) :
    BasePresenter<SearchResultsPresenter.View>() {
    private var recipes: List<Recipe>? = null

    fun search(query: String) {
        view?.showLoading()

        repository.getRecipes(query, object : RepositoryCallback<List<Recipe>> {
            override fun onSuccess(recipes: List<Recipe>?) {
                this@SearchResultsPresenter.recipes = recipes
                if (recipes != null && recipes.isNotEmpty()) {
                    view?.showRecipes(recipes)
                } else {
                    view?.showEmptyRecipes()
                }
                repository.saveRecentIngredients(query)
            }

            override fun onError() {
                view?.showError()
            }
        })
    }

    fun addFavorite(recipe: Recipe) {
        recipe.isFavourite = true

        repository.addFavourite(recipe)

        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }

    fun removeFavorite(recipe: Recipe) {
        repository.removeFavourite(recipe)
        recipe.isFavourite = false
        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }

    interface View {
        fun showLoading()
        fun showRecipes(recipes: List<Recipe>)
        fun showEmptyRecipes()
        fun showError()
        fun refreshFavoriteStatus(recipeIndex: Int)
    }
}
