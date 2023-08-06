package com.example.dinedash.activities.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.dinedash.R
import com.example.dinedash.databinding.FragmentHomeBinding
import com.example.dinedash.models.ProductCategory
import com.example.dinedash.models.TrendingFood
import com.example.dinedash.recyclers.ProductsRecycler
import com.example.dinedash.recyclers.TrendingFoodViewPager
import kotlin.collections.ArrayList
import kotlin.math.abs


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recycler: ProductsRecycler
    private lateinit var trendingViewPager: ViewPager2
    private lateinit var handler : Handler
    private var backPressedTime : Long = 0
    private val runnable = Runnable{
        trendingViewPager.currentItem = trendingViewPager.currentItem + 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackButton()
        handler = Handler(Looper.myLooper()!!)
        trendingViewPager = binding.trendingFoodVP
        setupTransformer()
        binding.trendingFoodVP.apply {
            adapter = TrendingFoodViewPager(getTrendingList(),trendingViewPager)
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if(handler !=null){
                    handler.apply {
                        removeCallbacks(runnable)
                        postDelayed(runnable, 3000)
                        }
                    }

                }
            }
            )
        }
        recycler = ProductsRecycler(getProductCategoryList())
        binding.productTypeRecycler.apply {
            adapter = recycler
            layoutManager = GridLayoutManager(requireContext(),4)
        }

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }





    private fun setupTransformer() {
        val transformer =CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        trendingViewPager.setPageTransformer(transformer)
    }


    private fun getTrendingList(): ArrayList<TrendingFood> {
        val list = listOf(
            TrendingFood(R.drawable.pexels_ash_376464, null),
            TrendingFood(R.drawable.pexels_chan_walrus_958545, null),
            TrendingFood(R.drawable.pexels_lgh_1256875, null),
            TrendingFood(R.drawable.pexels_foodie_factor_551997, null),
            TrendingFood(R.drawable.pexels_jane_doan_1099680, null),
            TrendingFood(R.drawable.pexels_robin_stickel_70497, null),
            TrendingFood(R.drawable.pexels_tranmautritam_61180, null),
        )
        return ArrayList(list)
    }


    private fun getProductCategoryList():List<ProductCategory> {
        return listOf(
            ProductCategory(null,"Television",null),
            ProductCategory(null,"Phones",null),
            ProductCategory(null,"Food",null),
            ProductCategory(null,"Fashion",null),
            ProductCategory(null,"Jewellery",null),
            ProductCategory(null,"Electronics",null),
            ProductCategory(null,"Sports",null),
            ProductCategory(null,"Groceries",null),
            ProductCategory(null,"Shoes",null),
            ProductCategory(null,"Airtime",null),
            ProductCategory(null,"Wristwatches",null),
            ProductCategory(null,"Laptops",null),
        )
    }


    private fun handleBackButton() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                val timeDifference = currentTime - backPressedTime

                if (timeDifference in 1..2000) {
                    isEnabled = false // Disable this callback to allow back button press to be handled by the system
                    activity?.onBackPressed() // Call the original onBackPressed method to exit the app
                } else {
                    backPressedTime = currentTime
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
}