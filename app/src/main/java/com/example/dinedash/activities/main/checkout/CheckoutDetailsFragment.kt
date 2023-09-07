package com.example.dinedash.activities.main.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dinedash.databinding.FragmentCheckoutDetailsBinding


/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutDetailsBinding

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
        binding.apply {
            continueButton.setOnClickListener {
                (parentFragment as? CheckoutFragment)?.navigateToSummaryPage()
            }
            cancelText.setOnClickListener {
                findNavController().navigateUp()
            }

        }
    }



}