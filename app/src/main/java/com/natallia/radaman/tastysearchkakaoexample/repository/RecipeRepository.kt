package com.natallia.radaman.tastysearchkakaoexample.repository

import com.natallia.radaman.tastysearchkakaoexample.common.Recipe

interface RecipeRepository {
    fun addFavourite(item: Recipe)
    fun removeFavourite(item: Recipe)
    fun getFavouriteRecipes(): List<Recipe>
    fun getRecipes(query: String, callback: RepositoryCallback<List<Recipe>>)
    fun saveRecentIngredients(query: String)
    fun getRecentIngredients(): List<String>
}

interface RepositoryCallback<in T> {
    fun onSuccess(t: T?)
    fun onError()
}
