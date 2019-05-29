package com.natallia.radaman.tastysearchkakaoexample

import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository
import com.natallia.radaman.tastysearchkakaoexample.repository.RepositoryCallback

class TastySearchKakaoTestApp : TastySearchKakaoApp() {

    override fun getRecipeRepository(): RecipeRepository {
        return object : RecipeRepository {
            override fun addFavourite(item: Recipe) {}
            override fun removeFavourite(item: Recipe) {}
            override fun getFavouriteRecipes() = emptyList<Recipe>()
            override fun saveRecentIngredients(query: String) {}

            override fun getRecipes(
                query: String,
                callback: RepositoryCallback<List<Recipe>>
            ) {
                val list = listOf(
                    buildRecipe(1, false),
                    buildRecipe(2, true),
                    buildRecipe(3, false),
                    buildRecipe(4, false),
                    buildRecipe(5, false),
                    buildRecipe(6, false),
                    buildRecipe(7, false),
                    buildRecipe(8, false),
                    buildRecipe(9, false),
                    buildRecipe(10, false)
                )
                callback.onSuccess(list)
            }

            override fun getRecentIngredients() =
                listOf("eggs", "ham", "onion", "tomato")
        }
    }

    private fun buildRecipe(id: Int, isFavourite: Boolean) =
        Recipe(id.toString(), "Title $id", "", "", isFavourite)
}
