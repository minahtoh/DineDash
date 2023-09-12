package com.example.dinedash.activities.main.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        }

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