package com.example.dinedash.activities.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinedash.databinding.FragmentProductsBinding
import com.example.dinedash.models.Product
import com.example.dinedash.models.ProductCategory
import com.example.dinedash.recyclers.CategoryProductsRecycler


/**
 * A simple [Fragment] subclass.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment() {
    private lateinit var binding:FragmentProductsBinding
    private lateinit var recycler:CategoryProductsRecycler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = CategoryProductsRecycler(getCategoryList())
        binding.productsRecycler.apply {
            adapter = recycler
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getCategoryList(): List<ProductCategory> {
        return listOf(
            ProductCategory(
                listOf(
                    Product("samsung", null),
                    Product("LG", null),
                    Product("Hisonic", null),
                    Product("Sony", null)
                ), "Television",null),
            ProductCategory(
                listOf(
                    Product("Chicken", null),
                    Product("Turkey", null),
                    Product("Beef", null),
                    Product("Pork", null)
                ), "Food",null),
            ProductCategory(
                listOf(
                    Product("samsung", null),
                    Product("LG", null),
                    Product("Apple", null),
                    Product("Sony", null),
                    Product("Nokia", null),
                ), "Phones",null),
            ProductCategory(
                listOf(
                    Product("Football", null),
                    Product("Basketball", null),
                    Product("Table-Tennis", null),
                    Product("Hockey", null)
                ), "Sports",null),
            ProductCategory(
                listOf(
                    Product("Wristwatches", null),
                    Product("Necklaces", null),
                    Product("Ankle Chain", null),
                    Product("Bangles", null)
                ), "Jewellery",null),
            ProductCategory(
                listOf(
                    Product("samsung", null),
                    Product("LG", null),
                    Product("Hisonic", null),
                    Product("Sony", null)
                ), "Television",null),
            ProductCategory(
                listOf(
                    Product("samsung", null),
                    Product("LG", null),
                    Product("Hisonic", null),
                    Product("Sony", null)
                ), "Television",null),
            ProductCategory(
                listOf(
                    Product("samsung", null),
                    Product("LG", null),
                    Product("Hisonic", null),
                    Product("Sony", null)
                ), "Television",null),

        )
    }


}