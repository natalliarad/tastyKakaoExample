package com.natallia.radaman.tastysearchkakaoexample.recentIngredients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.natallia.radaman.tastysearchkakaoexample.R
import com.natallia.radaman.tastysearchkakaoexample.common.ChildActivity
import com.natallia.radaman.tastysearchkakaoexample.repository.RecipeRepositoryImpl
import kotlinx.android.synthetic.main.activity_recent_ingredients.*

fun Context.recentIngredientsIntent(): Intent {
    return Intent(this, RecentIngredientsActivity::class.java)
}

class RecentIngredientsActivity : ChildActivity(), RecentIngredientsPresenter.View {

    private val presenter by lazy {
        RecentIngredientsPresenter(RecipeRepositoryImpl.getRepository(this))
    }

    companion object {
        const val EXTRA_INGREDIENTS_SELECTED = "EXTRA_INGREDIENTS_SELECTED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_ingredients)

        presenter.attachView(this)

        presenter.loadRecentIngredients()

        okButton.setOnClickListener {
            presenter.confirmSelectedIngredients()
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    // region RecentIngredientsPresenter.View methods
    override fun showRecentIngredients(list: List<String>) {
        ingredients.layoutManager = LinearLayoutManager(this)
        ingredients.adapter = RecentIngredientsAdapter(list,
            object : RecentIngredientsAdapter.Listener {
                override fun onSelectIngredient(ingredient: String) {
                    presenter.selectIngredient(ingredient)
                }

                override fun onUnselectIngredient(ingredient: String) {
                    presenter.unselectIngredient(ingredient)
                }
            })
    }

    override fun sendSelectedIngredients(list: List<String>) {
        val data = Intent()
        data.putStringArrayListExtra(EXTRA_INGREDIENTS_SELECTED, ArrayList(list))
        setResult(RESULT_OK, data)
        finish()
    }
    // endregion
}
