package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dinedash.databinding.ProductTypeBinding
import com.example.dinedash.models.ProductCategory
import com.example.dinedash.models.ProductType

class ProductsRecycler:RecyclerView.Adapter<ProductsRecycler.ProductsViewHolder>() {

    private val items: MutableList<ProductCategory> = mutableListOf()

    fun submitList(newItems: List<ProductCategory>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ProductsViewHolder(var binding: ProductTypeBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(productType: ProductCategory){
                binding.apply {
                    productName.text = productType.productID
                    Glide.with(itemView.context).load(productType.productImage)
                        .into(productImage)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(ProductTypeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = items[position]
        holder.bind(product)
    }
}