package com.natallia.radaman.tastysearchkakaoexample.search

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepository
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class SearchTests {

    private lateinit var repository: RecipeRepository
    private lateinit var presenter: SearchPresenter
    private lateinit var view: SearchPresenter.View

    @Before
    fun setup() {
        repository = mock()
        presenter = SearchPresenter(repository)
        view = mock()
        presenter.attachView(view)
    }

    @Test
    fun search_withEmptyQuery_callsShowQueryRequiredMessage() {
        presenter.search("")

        verify(view).showQueryRequiredMessage()
    }

    @Test
    fun search_withEmptyQuery_doesNotCallsShowSearchResults() {
        presenter.search("")

        verify(view, never()).showSearchResults(anyString())
    }
}