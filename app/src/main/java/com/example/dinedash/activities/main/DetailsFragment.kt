package com.example.dinedash.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        var isProductInCart = false


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
        theViewModel.getProductByName(product.productItemName)
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
                isClickable = false
            }
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
            shoppingCart.setOnClickListener {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToCartFragment())
            }

            buyButton.setOnClickListener {
                theViewModel.apply {
                    addToCart(product, this@DetailsFragment)
                    getProductByName(product.productItemName)
                }
                isProductInCart = true
            }
            //Check whether product already in cart
            theViewModel.getShoppingList().observe(viewLifecycleOwner){ productList->
                isProductInCart = productList.any {
                    it.productItemName == product.productItemName
                }
                if (isProductInCart){
                    countConstraint.visibility = View.VISIBLE
                    buyButton.visibility = View.GONE
                }
            }

            cartCount.apply {
                theViewModel.getProductCount().observe(viewLifecycleOwner){
                    text = it.toString()
                }
            }
            addButton.apply {
                setOnClickListener {
                    if (product.numberLeft!! > product.quantity) {
                        addButton.isEnabled = true
                        theViewModel.increaseQuantity(product)
                    } else {
                        Toast.makeText(requireContext(), "No more of this product available!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        addButton.isEnabled = false
                    }
                }
            }
            removeButton.apply {
                if (product.quantity >= 2) {
                    isEnabled = true
                    setOnClickListener { theViewModel.reduceQuantity(product) }
                } else {
                    isEnabled = false
                }
            }


            quantityNumber.apply {
               theViewModel.productByName.observe(viewLifecycleOwner){ product->
                   if (product != null) {
                       text = product.quantity.toString()
                   } else text = "0"

               }
            }


        }
    }



}