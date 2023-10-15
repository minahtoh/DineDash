package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dinedash.databinding.FlashSalesCardBinding
import com.example.dinedash.models.Product

class FlashRecycler: RecyclerView.Adapter<FlashRecycler.TheViewHolder>() {
    private val items: MutableList<Product> = mutableListOf()

    fun submitList(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    inner class TheViewHolder(private var binding: FlashSalesCardBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(product:Product){
                binding.apply {
                    productName.text = product.productItemName
                    productPrice.text = product.productPrice.toString()
                    Glide.with(itemView).load(product.productImage).into(productImage)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
       return TheViewHolder(FlashSalesCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        val product = items[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(product) }
       }
    }

    private var onItemClickListener:((Product?)->Unit)? = null
    fun setOnItemClickListener(listener:(Product?) -> Unit){
        onItemClickListener = listener
    }
}