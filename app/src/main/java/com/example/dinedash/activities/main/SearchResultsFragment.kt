package com.example.dinedash.activities.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinedash.databinding.FragmentSearchResultsBinding
import com.example.dinedash.models.Product
import com.example.dinedash.recyclers.SearchResultsRecycler

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultsFragment : Fragment() {
    private lateinit var binding : FragmentSearchResultsBinding
    private lateinit var recycler: SearchResultsRecycler

    companion object {
        private const val ARG_PRODUCTS = "arg_products"

        fun newInstance(products: List<Product>?): SearchResultsFragment {
            val args = Bundle()
            args.putSerializable(ARG_PRODUCTS, products?.let { ArrayList(it) })

            val fragment = SearchResultsFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = SearchResultsRecycler()
        binding.searchRecyclerView.apply {
            adapter = recycler
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
        val products = arguments?.getSerializable(ARG_PRODUCTS) as? List<Product>
        products?.let {
            recycler.submitList(it)
        }
        recycler.setOnItemClickListener {product->
            val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToDetailsFragment(product)
            val currentDestination = findNavController().currentDestination?.label
            Log.d("NavigationDebug", "Current destination: $currentDestination")
            findNavController().navigate(action)
        }

    }


}