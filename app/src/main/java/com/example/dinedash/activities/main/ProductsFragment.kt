package com.example.dinedash.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinedash.databinding.FragmentProductsBinding
import com.example.dinedash.recyclers.CategoryProductsRecycler
import com.example.dinedash.recyclers.ChildProductsRecycler
import com.example.dinedash.viewmodel.DineDashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment() {
    private lateinit var binding:FragmentProductsBinding
    private lateinit var recycler:CategoryProductsRecycler
    private val theViewModel : DineDashViewModel by activityViewModels()

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
        recycler = CategoryProductsRecycler()
        binding.productsRecycler.apply {
            adapter = recycler
            layoutManager = LinearLayoutManager(requireContext())
            theViewModel.productCategory.observe(viewLifecycleOwner){
                recycler.submitList(it)
            }
        }

    }


}