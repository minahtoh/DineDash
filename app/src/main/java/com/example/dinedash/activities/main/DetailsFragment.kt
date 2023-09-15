package com.example.dinedash.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.dinedash.databinding.FragmentDetailsBinding
import com.example.dinedash.viewmodel.DineDashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
        private lateinit var binding:FragmentDetailsBinding
        private val args : DetailsFragmentArgs by navArgs()
        private val theViewModel : DineDashViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product
        binding.apply {
            productDescription.text = product.productBrandName + " " + product.productItemName
            productBrand.append(product.productBrandName)
            productPrice.text = product.productPrice.toString()
            Glide.with(requireContext()).load(product.productImage).into(productImageView)
            ratingIndicator.apply {
                val productRating = product.numberLeft
                val progressPercentage = 1- (productRating!! / 100)
                progress = progressPercentage.toInt()
            }
            starRating.apply {
                rating = product.productRating.toInt()
            }
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
            shoppingCart.setOnClickListener {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToCartFragment())
            }
            buyButton.setOnClickListener {
                theViewModel.addToCart(product, this@DetailsFragment)
            }
            cartCount.apply {
                theViewModel.getProductCount().observe(viewLifecycleOwner){
                    text = it.toString()
                }
            }
        }
    }

}