package com.natallia.radaman.tastysearchkakaoexample.favourites

import com.natallia.radaman.tastysearchkakaoexample.common.BasePresenter
import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository

class FavouritesPresenter(private val repository: RecipeRepository) :
    BasePresenter<FavouritesPresenter.View>() {

    fun loadFavourites() {
        val recipes = repository.getFavouriteRecipes()
        if (recipes.isNotEmpty()) {
            view?.showRecipes(recipes)
        } else {
            view?.showEmptyRecipes()
        }
    }

    fun removeFavourite(recipe: Recipe) {
        val recipes = repository.getFavouriteRecipes()
        val recipeIndex = recipes.indexOf(recipe)

        repository.removeFavourite(recipe)
        recipe.isFavourite = false

        view?.refreshRemovedFavourite(recipeIndex)
    }

    interface View {
        fun showRecipes(recipes: List<Recipe>)
        fun showEmptyRecipes()
        fun refreshRemovedFavourite(recipeIndex: Int)
    }
}
