package com.natallia.radaman.tastysearchkakaoexample.recentIngredients

import com.natallia.radaman.tastysearchkakaoexample.common.BasePresenter
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository

class RecentIngredientsPresenter(private val repository: RecipeRepository) :
    BasePresenter<RecentIngredientsPresenter.View>() {

    private val selectedIngredients = mutableListOf<String>()

    fun loadRecentIngredients() {
        val recentIngredients = repository.getRecentIngredients()
        view?.showRecentIngredients(recentIngredients)
    }

    fun selectIngredient(ingredient: String) {
        selectedIngredients.add(ingredient)
    }

    fun unselectIngredient(ingredient: String) {
        selectedIngredients.remove(ingredient)
    }

    fun confirmSelectedIngredients() {
        view?.sendSelectedIngredients(selectedIngredients)
    }

    interface View {
        fun showRecentIngredients(list: List<String>)
        fun sendSelectedIngredients(list: List<String>)
    }
}
