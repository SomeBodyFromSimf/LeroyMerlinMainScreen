package com.mihailchistousov.leroymerlin.screens.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mihailchistousov.leroymerlin.R
import com.mihailchistousov.leroymerlin.models.Category
import kotlinx.android.synthetic.main.row_categories_item.view.*

class CategoryAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var list: MutableList<Category> = mutableListOf()

    var onCatalogClick: (() -> Unit)? = null
    var onAllClick: (() -> Unit)? = null
    var onItemClick: ((Int, String) -> Unit)? = null

    fun submitList(l: List<Category>) {
        list.clear()
        list.addAll(l)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> TYPE_HEADER
        6 -> TYPE_FOOTER
        else -> TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_HEADER -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_categories_header, parent, false))
        TYPE_FOOTER -> FooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_categories_footer, parent, false))
        else -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_categories_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> list.getOrNull(position-1)?.let(holder::bind)
        }
    }

    override fun getItemCount(): Int = if(list.count() > 5) 7 else list.count()


    inner class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { onCatalogClick?.invoke() }
        }
    }

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(category: Category) {
            view.setOnClickListener { onItemClick?.invoke(category.id,category.title) }
            view.title.text = category.title
        }
    }

    inner class FooterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { onAllClick?.invoke() }
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }
}