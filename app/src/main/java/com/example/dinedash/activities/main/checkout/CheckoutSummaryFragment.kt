package com.example.dinedash.activities.main.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dinedash.databinding.FragmentCheckoutSummaryBinding
import com.example.dinedash.utils.Constants.DELIVERY_FEE
import com.example.dinedash.viewmodel.DineDashViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutSummaryFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutSummaryBinding
    private val theViewModel: DineDashViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutSummaryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backText.setOnClickListener {
            (parentFragment as? CheckoutFragment)?.navigateToDetailsPage()
                }
            subtotalText.apply {
                theViewModel.getPricesList().observe(viewLifecycleOwner){list->
                    text = list.sum().toString()
                }
            }
            deliveryText.apply {
                text = DELIVERY_FEE.toString()
            }
            totalPrice.apply {
                theViewModel.getPricesList().observe(viewLifecycleOwner){list->
                    val  total = list.sum().plus(DELIVERY_FEE)
                    text = total.toString()
                }
            }
            theViewModel.paymentDetails.observe(viewLifecycleOwner){
                cardNumber.text = it.number
                cardExpiry.text = it.expiryDate
            }
            submitButton.setOnClickListener {
                submitOrder()
            }
        }
    }

    private fun submitOrder() {
        MaterialAlertDialogBuilder(requireContext())
        .setTitle("Attention")
        .setMessage("Submit Order?")
        .setCancelable(false)
        .setNegativeButton("No") { _, _ -> }
        .setPositiveButton("Yes") { _, _ ->

            Snackbar.
            make(requireView(),"Your Order has been submitted successfully!", Snackbar.LENGTH_SHORT).
            show()
        }
        .show()
    }
}