package com.example.dinedash.activities.main.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dinedash.databinding.FragmentCheckoutBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {
    private lateinit var binding:FragmentCheckoutBinding
    private lateinit var adapter: FragmentStateAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater,container,false)
        adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return if(position == 0){
                    CheckoutDetailsFragment()
                }else{
                    CheckoutSummaryFragment()
                }
            }

        }
        binding.detailsViewpager.adapter = adapter

        return binding.root
    }
    fun navigateToSummaryPage() {
        // Switch to the summary page (position 1) in the ViewPager
        binding.detailsViewpager.currentItem = 1
    }
    fun navigateToDetailsPage(){
        binding.detailsViewpager.currentItem = 0
    }
}