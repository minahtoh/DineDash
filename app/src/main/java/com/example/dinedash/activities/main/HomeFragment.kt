package com.example.dinedash.activities.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinedash.R
import com.example.dinedash.databinding.FragmentHomeBinding
import com.example.dinedash.models.ProductCategory
import com.example.dinedash.recyclers.ProductsRecycler


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recycler: ProductsRecycler
    private var backPressedTime :Long = 0

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
        recycler = ProductsRecycler(getProductCategoryList())
        binding.productTypeRecycler.apply {
            adapter = recycler
            layoutManager = GridLayoutManager(requireContext(),4)
        }


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