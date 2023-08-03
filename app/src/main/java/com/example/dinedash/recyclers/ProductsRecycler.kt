package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dinedash.databinding.ProductTypeBinding
import com.example.dinedash.models.ProductCategory

class ProductsRecycler(private val productList: List<ProductCategory>)
    :RecyclerView.Adapter<ProductsRecycler.ProductsViewHolder>() {

    inner class ProductsViewHolder(var binding: ProductTypeBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(productType: ProductCategory){
                binding.apply {
                    productName.text = productType.type
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(ProductTypeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }
}