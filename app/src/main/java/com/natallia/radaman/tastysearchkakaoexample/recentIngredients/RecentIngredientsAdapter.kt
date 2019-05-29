package com.natallia.radaman.tastysearchkakaoexample.recentIngredients

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natallia.radaman.tastysearchkakaoexample.R
import kotlinx.android.synthetic.main.listitem_recent_ingredient.view.*

class RecentIngredientsAdapter(
    private var items: List<String>,
    private val listener: Listener
) : RecyclerView.Adapter<RecentIngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_recent_ingredient, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], listener)

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox = itemView.checkbox

        fun bind(item: String, listener: Listener) {
            checkbox.text = item
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    listener.onSelectIngredient(item)
                } else {
                    listener.onUnselectIngredient(item)
                }
            }
        }
    }

    interface Listener {
        fun onSelectIngredient(ingredient: String)
        fun onUnselectIngredient(ingredient: String)
    }
}
