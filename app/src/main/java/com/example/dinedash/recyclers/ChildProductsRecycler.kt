package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dinedash.databinding.ProductTypeBinding
import com.example.dinedash.models.Product

class ChildProductsRecycler(private val productList:List<Product>):
    RecyclerView.Adapter<ChildProductsRecycler.TheViewHolder>() {
    inner class TheViewHolder(var binding: ProductTypeBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(product: Product){
                    binding.apply {
                        productName.text = product.productBrandName
                        Glide.with(itemView).load(product.productImage).into(productImage)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        return TheViewHolder(ProductTypeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
      return productList.size
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(product) }
        }
    }

    private var onItemClickListener:((Product)->Unit)? = null
    fun setOnItemClickListener(listener:(Product) -> Unit){
        onItemClickListener = listener
    }
}