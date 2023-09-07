package com.example.dinedash.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinedash.R
import com.example.dinedash.databinding.FragmentCartBinding
import com.example.dinedash.models.Product
import com.example.dinedash.recyclers.CartItemCallback
import com.example.dinedash.recyclers.CartRecycler
import com.example.dinedash.viewmodel.DineDashViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(), CartItemCallback {
        private lateinit var binding: FragmentCartBinding
        private lateinit var recycler : CartRecycler
        private val theViewModel: DineDashViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)
        ViewTreeLifecycleOwner.set(binding.root,viewLifecycleOwner)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = CartRecycler(this)
//        showOptionsMenu()

        binding.apply {
            cartRecycler.apply {
                adapter = recycler
                layoutManager = LinearLayoutManager(requireContext())
                theViewModel.getShoppingList().observe(viewLifecycleOwner){
                    recycler.submitList(it)
                }
            }
            theViewModel.getProductCount().observe(viewLifecycleOwner){
                cartText.text = "CART($it)"
            }
            totalPrice.apply {
                theViewModel.getPricesList().observe(viewLifecycleOwner){ list->
                    text = list.sum().toString()
                }
            }
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
            menuText.setOnClickListener{
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Attention")
                    .setMessage("Clear Shopping Cart? (This cannot be undone)")
                    .setCancelable(false)
                    .setNegativeButton("No") { _, _ -> }
                    .setPositiveButton("Yes") { _, _ ->
                        theViewModel.clearCart()
                        Snackbar.
                        make(requireView(),"Shopping cart cleared!", Snackbar.LENGTH_SHORT).
                        show()
                    }
                    .show()
            }
            checkoutButton.setOnClickListener {
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckoutFragment())
            }
        }
    }


    private fun showOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.cart_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId){
                    R.id.clear_cart ->{
                        return true
                    }
                }
                return false
            }

        },viewLifecycleOwner)
    }


    override fun onCartItemDeleteClicked(productId: Product) {
        showConfirmationDialog(productId,this)
    }

    override fun increaseQuantity(product: Product) {
        theViewModel.increaseQuantity(product)
    }

    override fun reduceQuantity(product: Product) {
        theViewModel.reduceQuantity(product)
    }


    private fun showConfirmationDialog(product:Product, fragment: Fragment) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Attention")
            .setMessage("Remove from cart?(This cannot be undone)")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                theViewModel.removeFromCart(product,fragment)
                Snackbar.
                make(requireView(),"${product.productItemName} removed from cart!", Snackbar.LENGTH_SHORT).
                    show()
            }
            .show()
    }

}