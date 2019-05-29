package com.natallia.radaman.tastysearchkakaoexample.common

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.natallia.radaman.tastysearchkakaoexample.R
import kotlinx.android.synthetic.main.listitem_recipe.view.*

class RecipeAdapter(private var items: List<Recipe>, private val listener: Listener) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_recipe, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], listener)

    override fun getItemCount() = items.size

    fun removeItem(index: Int) {
        items -= items[index]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.imageView
        private val title = itemView.title
        private val favButton = itemView.favButton

        fun bind(item: Recipe, listener: Listener) {
            Glide.with(itemView.context).load(item.imageUrl).into(imageView)
            title.text = item.title

            if (item.isFavourite) {
                favButton.setImageResource(R.drawable.ic_favorite_24dp)
            } else {
                favButton.setImageResource(R.drawable.ic_favorite_border_24dp)
            }

            itemView.setOnClickListener {
                listener.onClickItem(item)
            }

            favButton.setOnClickListener {
                if (item.isFavourite) {
                    listener.onRemoveFavourite(item)
                } else {
                    listener.onAddFavourite(item)
                }
            }
        }
    }

    interface Listener {
        fun onClickItem(item: Recipe)
        fun onAddFavourite(item: Recipe)
        fun onRemoveFavourite(item: Recipe)
    }
}
