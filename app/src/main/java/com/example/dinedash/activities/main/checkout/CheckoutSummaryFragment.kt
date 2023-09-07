package com.example.dinedash.activities.main.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dinedash.databinding.FragmentCheckoutSummaryBinding


/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutSummaryFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutSummaryBinding
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
        binding.backText.setOnClickListener {
            (parentFragment as? CheckoutFragment)?.navigateToDetailsPage()
        }
    }
}