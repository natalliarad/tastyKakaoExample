package com.natallia.radaman.tastysearchkakaoexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.natallia.radaman.tastysearchkakaoexample.favourites.FavouritesActivity
import com.natallia.radaman.tastysearchkakaoexample.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        favButton.setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
    }
}
