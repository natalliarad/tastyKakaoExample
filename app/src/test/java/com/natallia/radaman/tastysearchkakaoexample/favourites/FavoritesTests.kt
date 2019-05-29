package com.natallia.radaman.tastysearchkakaoexample.favourites

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.natallia.radaman.tastysearchkakaoexample.common.Recipe
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository
import org.junit.Before
import org.junit.Test

class FavoritesTests {

    private lateinit var repository: RecipeRepository
    private lateinit var presenter: FavouritesPresenter
    private lateinit var view: FavouritesPresenter.View

    @Before
    fun setup() {
        repository = mock()
        view = mock()
        presenter = FavouritesPresenter(repository)
        presenter.attachView(view)
    }

    @Test
    fun loadFavorites_callsGetFavorites() {
        presenter.loadFavourites()

        verify(repository).getFavouriteRecipes()
    }

    @Test
    fun loadFavorites_withRepositoryHavingFavorites_callsShowRecipes() {
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
        val recipes = listOf(recipe)
        whenever(repository.getFavouriteRecipes()).thenReturn(recipes)

        presenter.loadFavourites()

        verify(view).showRecipes(eq(recipes))
    }

    @Test
    fun loadFavorites_withEmptyRepository_callsShowEmptyRecipes() {
        whenever(repository.getFavouriteRecipes()).thenReturn(emptyList())

        presenter.loadFavourites()

        verify(view).showEmptyRecipes()
    }

    @Test
    fun removeFavorite_callsRefreshRemovedFavorite() {
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
        val recipes = listOf(recipe)
        whenever(repository.getFavouriteRecipes()).thenReturn(recipes)

        presenter.removeFavourite(recipe)

        verify(view).refreshRemovedFavourite(eq(0))
    }

    @Test
    fun removeFavorite_callsRepositoryRemove() {
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
        val recipes = listOf(recipe)
        whenever(repository.getFavouriteRecipes()).thenReturn(recipes)

        presenter.removeFavourite(recipe)

        verify(repository).removeFavourite(eq(recipe))
    }
}
