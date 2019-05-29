package com.natallia.radaman.tastysearchkakaoexample.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import com.natallia.radaman.tastysearchkakaoexample.common.RecipesContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val FAVORITES_KEY = "FAVORITES_KEY"
private const val RECENT_INGREDIENTS_KEY = "RECENT_INGREDIENTS_KEY"

class RecipeRepositoryImpl(private val sharedPreferences: SharedPreferences) : RecipeRepository {

    private val gson = Gson()

    override fun addFavourite(item: Recipe) {
        val favorites = getFavouriteRecipes() + item
        saveFavorites(favorites)
    }

    override fun removeFavourite(item: Recipe) {
        val favorites = getFavouriteRecipes() - item
        saveFavorites(favorites)
    }

    private fun saveFavorites(favorites: List<Recipe>) {
        val editor = sharedPreferences.edit()
        editor.putString(FAVORITES_KEY, gson.toJson(favorites))
        editor.apply()
    }

    private inline fun <reified T> Gson.fromJson(json: String): T =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    override fun getFavouriteRecipes(): List<Recipe> {
        val favoritesString = sharedPreferences.getString(FAVORITES_KEY, null)
        if (favoritesString != null) {
            return gson.fromJson(favoritesString)
        }

        return emptyList()
    }

    override fun getRecipes(query: String, callback: RepositoryCallback<List<Recipe>>) {
        val call = RecipeApi.create().search(query)
        call.enqueue(object : Callback<RecipesContainer> {
            override fun onResponse(
                call: Call<RecipesContainer>?,
                response: Response<RecipesContainer>?
            ) {
                if (response != null && response.isSuccessful) {
                    val recipesContainer = response.body()
                    markFavorites(recipesContainer)
                    callback.onSuccess(recipesContainer?.recipes)
                } else {
                    callback.onError()
                }
            }

            override fun onFailure(call: Call<RecipesContainer>?, t: Throwable?) {
                callback.onError()
            }
        })
    }

    private fun markFavorites(recipesContainer: RecipesContainer?) {
        if (recipesContainer != null) {
            val favoriteRecipes = getFavouriteRecipes()
            if (favoriteRecipes.isNotEmpty()) {
                for (item in recipesContainer.recipes) {
                    item.isFavourite = favoriteRecipes.map { it.recipeId }.contains(item.recipeId)
                }
            }
        }
    }

    override fun getRecentIngredients(): List<String> {
        val recentIngredientsString =
            sharedPreferences.getString(RECENT_INGREDIENTS_KEY, null)
        if (recentIngredientsString != null) {
            return gson.fromJson(recentIngredientsString)
        }

        return emptyList()
    }

    override fun saveRecentIngredients(query: String) {
        val ingredients = query.split(",")
        val recentIngredients = getRecentIngredients().toMutableList()
        ingredients.forEach {
            val ingredient = it.toLowerCase().trim()
            if (!recentIngredients.contains(ingredient)) {
                recentIngredients.add(ingredient)
            }
        }

        recentIngredients.sort()
        val editor = sharedPreferences.edit()
        editor.putString(RECENT_INGREDIENTS_KEY, gson.toJson(recentIngredients))
        editor.apply()
    }

    companion object {
        fun getRepository(context: Context): RecipeRepositoryImpl {
            return RecipeRepositoryImpl(
                context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
            )
        }
    }
}
