package com.example.dinedash.activities.main.checkout

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinedash.databinding.AddressSelectionDialogBinding
import com.example.dinedash.databinding.FragmentCheckoutDetailsBinding
import com.example.dinedash.models.PaymentDetails
import com.example.dinedash.recyclers.CheckoutRecycler
import com.example.dinedash.viewmodel.DineDashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutDetailsBinding
    private val theViewModel : DineDashViewModel by activityViewModels()
    private lateinit var recycler : CheckoutRecycler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = CheckoutRecycler()
        binding.apply {
            continueButton.setOnClickListener {
                getPaymentDetails()
                (parentFragment as? CheckoutFragment)?.navigateToSummaryPage()
            }
            cancelText.setOnClickListener {
                findNavController().navigateUp()
            }
            orderList.apply {
                adapter = recycler
                layoutManager = LinearLayoutManager(context)
                theViewModel.getShoppingList().observe(viewLifecycleOwner){
                    recycler.submitList(it)
                }
            }
            addressSelector.apply {
                setOnClickListener {
                    setupAddressSelector()
                    theViewModel.address.observe(viewLifecycleOwner){
                        selectedAddress.text = it
                    }
                    visibility = View.INVISIBLE
                }
            }

        }

    }

    private fun setupAddressSelector() {
        // Inflate the custom layout
        val dialogBinding = AddressSelectionDialogBinding.inflate(LayoutInflater.from(requireContext()))


// Create and configure the AlertDialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogBinding.root)
        builder.setTitle("Select an Address")
        builder.setPositiveButton("OK") { _, _ ->
            // Handle the selection when the OK button is clicked
            val selectedRadioButtonId = dialogBinding.addressRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = dialogBinding.root.findViewById<RadioButton>(selectedRadioButtonId)
                val selectedAddress = selectedRadioButton.text.toString()
                // Do something with the selected address
                theViewModel.address.postValue(selectedAddress)
            }
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            // Handle the cancellation
        }
        val alertDialog = builder.create()

// Show the dialog
        alertDialog.show()

    }


    private fun getPaymentDetails(){
        binding.apply {
            val name = cardName.text.toString().trim { it <= ' ' }
            val number = cardNumberText.text.toString().trim { it <= ' ' }
            val expiry = cardExpiryDate.text.toString().trim { it <= ' ' }
            val cvv = cardCvv.text.toString().trim { it <= ' ' }
            val newPaymentDetails = PaymentDetails(name,number,expiry,cvv)
            theViewModel.paymentDetails.postValue(newPaymentDetails)
        }
    }
}