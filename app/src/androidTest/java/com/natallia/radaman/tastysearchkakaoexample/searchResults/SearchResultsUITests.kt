package com.natallia.radaman.tastysearchkakaoexample.searchResults

import android.content.Intent
import android.view.View
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.natallia.radaman.tastysearchkakaoexample.R
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test

class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
    val title = KTextView(parent) { withId(R.id.title) }
    val favButton = KImageView(parent) { withId(R.id.favButton) }
}

class SearchResultsScreen : Screen<SearchResultsScreen>() {
    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.list)
    }, itemTypeBuilder = {
        itemType(::Item)
    })
}

@LargeTest
class SearchResultsUITests {

    @Rule
    @JvmField
    var rule: ActivityTestRule<SearchResultsActivity> =
        object : ActivityTestRule<SearchResultsActivity>(SearchResultsActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, SearchResultsActivity::class.java)
                result.putExtra("EXTRA_QUERY", "eggs, tomato")
                return result
            }
        }

    private val screen = SearchResultsScreen()

    @Test
    fun shouldRenderRecipesFromRepository() {
        screen {
            recycler {
                hasSize(10)
            }
        }
    }

    @Test
    fun shouldRenderTitleAndFavorite() {
        screen {
            recycler {
                for (i in 0..9) {
                    scrollTo(i)
                    childAt<Item>(i) {
                        title.hasText("Title " + (i + 1))
                        if (i != 1) {
                            favButton.hasDrawable(R.drawable.ic_favorite_border_24dp)
                        } else {
                            favButton.hasDrawable(R.drawable.ic_favorite_24dp)
                        }
                    }
                }
            }
        }
    }
}
