package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinedash.databinding.ProductParentBinding
import com.example.dinedash.models.ProductCategory

class CategoryProductsRecycler(private val categoriesList: List<ProductCategory>):
    RecyclerView.Adapter<CategoryProductsRecycler.TheViewHolder>() {
    inner class TheViewHolder(var binding:ProductParentBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(product: ProductCategory){
                    val adapter = product.products?.let { ChildProductsRecycler(it) }
                    val isExpandable = product.isExpandable

                    binding.apply {
                        productTitle.text = product.type
                        productChildRecycler.setHasFixedSize(true)
                        productChildRecycler.layoutManager = GridLayoutManager(itemView.context, 3)
                        productChildRecycler.adapter = adapter
                        productChildRecycler.visibility = if (isExpandable) View.VISIBLE else View.GONE

                        parentConstraint.setOnClickListener {
                            product.isExpandable = !product.isExpandable
                            notifyItemChanged(layoutPosition)
                        }
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
       return TheViewHolder(ProductParentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
      return categoriesList.size
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        val product = categoriesList[position]
        holder.bind(product)
    }
}