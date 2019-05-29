package com.natallia.radaman.tastysearchkakaoexample.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import org.junit.Before
import org.junit.Test

class RepositoryTests {
    private lateinit var spyRepository: RecipeRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)

        spyRepository = spy(RecipeRepositoryImpl(sharedPreferences))
    }

    @Test
    fun addFavorite_withEmptyRecipes_savesJsonRecipe() {
        doReturn(emptyList<Recipe>()).whenever(spyRepository).getFavouriteRecipes()

        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
        spyRepository.addFavourite(recipe)

        inOrder(sharedPreferencesEditor) {
            val jsonString = Gson().toJson(listOf(recipe))
            verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
            verify(sharedPreferencesEditor).apply()
        }
    }

    @Test
    fun saveRecentIngredients_withEmptyRecentIngredients_savesJsonList() {
        doReturn(emptyList<String>()).whenever(spyRepository).getRecentIngredients()

        val query = "Eggs, ham, CHEESE"

        spyRepository.saveRecentIngredients(query)
        inOrder(sharedPreferencesEditor) {
            val jsonString = Gson().toJson(listOf("cheese", "eggs", "ham"))
            verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
            verify(sharedPreferencesEditor).apply()
        }
    }
}