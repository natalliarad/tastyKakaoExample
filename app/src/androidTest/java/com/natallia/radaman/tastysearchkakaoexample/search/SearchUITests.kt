package com.natallia.radaman.tastysearchkakaoexample.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.filters.LargeTest
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.intent.KIntent
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.natallia.radaman.tastysearchkakaoexample.R
import com.natallia.radaman.tastysearchkakaoexample.recentIngredients.RecentIngredientsActivity
import com.natallia.radaman.tastysearchkakaoexample.searchResults.SearchResultsActivity
import org.junit.Rule
import org.junit.Test

class SearchScreen : Screen<SearchScreen>() {
    val ingredients = KEditText { withId(R.id.ingredients) }
    val searchButton = KButton { withId(R.id.searchButton) }
    val snackbar = KView { withId(com.google.android.material.R.id.snackbar_text) }
    val recentButton = KView { withId(R.id.recentButton) }
}

@LargeTest
class SearchUITests {

    @Rule
    @JvmField
    var rule = IntentsTestRule(SearchActivity::class.java)

    private val screen = SearchScreen()

    @Test
    fun search_withEmptyText_shouldShowSnackbarError() {
        screen {
            searchButton.click()
            snackbar.isDisplayed()
        }
    }

    @Test
    fun search_withText_shouldNotShowSnackbarError() {
        screen {
            ingredients.typeText("eggs, ham, cheese")
            searchButton.click()
            snackbar.doesNotExist()
        }
    }

    @Test
    fun search_withText_shouldLaunchSearchResults() {
        screen {
            val query = "eggs, ham, cheese"
            ingredients.typeText(query)
            searchButton.click()

            val searchResultsIntent = KIntent {
                hasComponent(SearchResultsActivity::class.java.name)
                hasExtra("EXTRA_QUERY", query)
            }
            searchResultsIntent.intended()
        }
    }

    @Test
    fun choosingRecentIngredients_shouldSetCommaSeparatedIngredients() {
        screen {
            val recentIngredientsIntent = KIntent {
                hasComponent(RecentIngredientsActivity::class.java.name)
                withResult {
                    withCode(RESULT_OK)
                    withData(
                        Intent().putStringArrayListExtra(
                            RecentIngredientsActivity.EXTRA_INGREDIENTS_SELECTED,
                            ArrayList(listOf("eggs", "onion"))
                        )
                    )
                }
            }
            recentIngredientsIntent.intending()

            recentButton.click()

            ingredients.hasText("eggs,onion")
        }
    }
}
