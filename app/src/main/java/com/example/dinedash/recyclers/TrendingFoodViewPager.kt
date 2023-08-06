package com.example.dinedash.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dinedash.databinding.ViewpagerContainerCardBinding
import com.example.dinedash.models.TrendingFood

class TrendingFoodViewPager(private val imageList: ArrayList<TrendingFood>, private val viewPager: ViewPager2):
            RecyclerView.Adapter<TrendingFoodViewPager.TheViewHolder>(){
    inner class TheViewHolder(var binding: ViewpagerContainerCardBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(image: TrendingFood){
                    binding.apply {
                        viewpagerImageView.setImageResource(image.image)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        return TheViewHolder(ViewpagerContainerCardBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        val image = imageList[position]
        holder.bind(image)
        if(position == imageList.size-1){
            viewPager.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}