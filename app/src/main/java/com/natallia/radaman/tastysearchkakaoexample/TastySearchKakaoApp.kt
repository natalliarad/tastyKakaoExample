package com.natallia.radaman.tastysearchkakaoexample

import android.app.Application
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepositoryImpl
import com.natallia.radaman.tastysearchkakaoexample.search.SearchPresenter
import com.natallia.radaman.tastysearchkakaoexample.searchResults.SearchResultsPresenter

open class TastySearchKakaoApp : Application() {

    open fun getRecipeRepository(): RecipeRepository {
        return RecipeRepositoryImpl.getRepository(this)
    }

    fun getSearchResultsPresenter() = SearchResultsPresenter(getRecipeRepository())

    fun getSearchPresenter() = SearchPresenter(getRecipeRepository())
}
