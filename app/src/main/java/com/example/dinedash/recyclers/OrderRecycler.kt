package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinedash.databinding.OrdersCardViewBinding
import com.example.dinedash.models.Order
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrderRecycler: RecyclerView.Adapter<OrderRecycler.TheViewHolder>() {
    private val orderList: MutableList<Order> = mutableListOf()

    fun submitList(newItems: List<Order>) {
        orderList.clear()
        orderList.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class TheViewHolder(private var binding:OrdersCardViewBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(order: Order){
                    val newAdapter = CheckoutRecycler()
                    newAdapter.submitList(order.productsBought)
                    var expandable = false
                    binding.parentConstraint.setOnClickListener {
                        expandable = expandable == false
                        notifyItemChanged(layoutPosition)
                    }
                    binding.orderListRecycler.apply {
                        adapter = newAdapter
                        layoutManager = LinearLayoutManager(context)
                        visibility = if (expandable) View.VISIBLE else View.GONE
                    }
                    binding.apply {
                        val time = LocalDateTime.ofInstant(Instant.ofEpochMilli(order.orderId), ZoneId.systemDefault())
                        val formatter = DateTimeFormatter.ofPattern("dd, MMM yyyy", Locale.getDefault())
                        val formattedDate = time.format(formatter)
                        orderDate.text = formattedDate
                        totalCostPrice.text = order.totalPrice.toString()
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
      return TheViewHolder(OrdersCardViewBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
       val order = orderList[position]
        holder.bind(order)
    }
}