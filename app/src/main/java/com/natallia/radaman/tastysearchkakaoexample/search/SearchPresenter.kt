package com.natallia.radaman.tastysearchkakaoexample.search

import com.natallia.radaman.tastysearchkakaoexample.common.BasePresenter
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository

class SearchPresenter(private val repository: RecipeRepository) :
    BasePresenter<SearchPresenter.View>() {

    fun search(query: String) {
        if (query.trim().isBlank()) {
            view?.showQueryRequiredMessage()
        } else {
            view?.showSearchResults(query)
        }
    }

    fun loadRecentIngredients() {
        if (repository.getRecentIngredients().isEmpty()) {
            view?.hideRecentIngredients()
        } else {
            view?.showRecentIngredients()
        }
    }

    interface View {
        fun showQueryRequiredMessage()
        fun showSearchResults(query: String)
        fun hideRecentIngredients()
        fun showRecentIngredients()
    }
}
