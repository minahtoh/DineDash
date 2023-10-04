package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dinedash.databinding.CartDisplayCardBinding
import com.example.dinedash.models.Product

class CartRecycler(private val callback: CartItemCallback): RecyclerView.Adapter<CartRecycler.ViewHolder>() {
    private val cartList: MutableList<Product> = mutableListOf()

    fun submitList(newItems: List<Product>) {
        cartList.clear()
        cartList.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CartDisplayCardBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(product: Product){
                    binding.apply {
                        productName.text = product.productBrandName + " " + product.productItemName
                        productPrice.text = product.productPrice.toString()
                        Glide.with(itemView).load(product.productImage).into(productImage)
                        deleteIcon.setOnClickListener {
                            callback.onCartItemDeleteClicked(product)
                        }
                        addButton.apply {
                            setOnClickListener{
                            if (product.numberLeft!! > product.quantity){
                                addButton.isEnabled = true
                                callback.increaseQuantity(product)
                                Toast.makeText(itemView.context, "Added one more", Toast.LENGTH_SHORT)
                                    .show()
                                } else{
                                    Toast.makeText(itemView.context, "No more of this product available!", Toast.LENGTH_SHORT)
                                        .show()
                                    addButton.isEnabled = false
                                }
                            }
                        }
                        removeButton.apply {
                            if (product.quantity >= 2){
                                isEnabled = true
                                setOnClickListener{ callback.reduceQuantity(product) }
                            } else{
                                isEnabled = false
                            }

                        }
                        quantityNumber.apply {
                           text = product.quantity.toString()
                        }
                    }
                }
            }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(CartDisplayCardBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = cartList[position]
        holder.bind(product)
    }

}

interface CartItemCallback {
    fun onCartItemDeleteClicked(productId: Product)
    fun increaseQuantity(product: Product)
    fun reduceQuantity(product: Product)
}