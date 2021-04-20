package com.mihailchistousov.leroymerlin.screens.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mihailchistousov.leroymerlin.R
import com.mihailchistousov.leroymerlin.models.Product
import kotlinx.android.synthetic.main.row_product_item.view.*
import kotlin.math.roundToInt


class ProductAdapter(
    private val context: Context
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var list: MutableList<Product> = mutableListOf()

    var onItemClick: ((Product) -> Unit)? = null

    fun submitList(l: List<Product>) {
        list.clear()
        list.addAll(l)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_product_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list.getOrNull(position)?.let(holder::bind)
    }

    override fun getItemCount(): Int = list.count()


    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            view.setOnClickListener { onItemClick?.invoke(product) }
            var price = product.price.toInt().toString()
            var coins: Int? = ((product.price - product.price.toInt())*100).roundToInt()
            if(coins==0) {
                coins = null
            } else {
                price = "$price,"
            }
            //view.image
            Glide.with(context)
                .load(product.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(view.image)
            view.tvPrice.text = price
            view.tvPriceAdditional.text = "${coins?:""} ₽/${product.quantity}"
            view.tvDescription.text = product.title
        }
    }
}