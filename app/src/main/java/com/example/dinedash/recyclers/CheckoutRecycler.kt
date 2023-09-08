package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dinedash.databinding.OrderListViewBinding
import com.example.dinedash.models.Product

class CheckoutRecycler:RecyclerView.Adapter<CheckoutRecycler.TheViewHolder>() {

    private val orderList: MutableList<Product> = mutableListOf()

    fun submitList(newItems: List<Product>) {
        orderList.clear()
        orderList.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class TheViewHolder(var binding: OrderListViewBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(product: Product){
                    binding.apply {
                        productName.text = product.productItemName
                        quantity.text = "${product.quantity}X"
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        return TheViewHolder(OrderListViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return orderList.size
    }


    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
    }
}