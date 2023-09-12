package com.example.dinedash.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinedash.databinding.FragmentOrderBinding
import com.example.dinedash.recyclers.OrderRecycler
import com.example.dinedash.viewmodel.DineDashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
    private lateinit var binding:FragmentOrderBinding
    private val theViewModel : DineDashViewModel by activityViewModels()
    private lateinit var recycler: OrderRecycler


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        theViewModel.getOrdersList(this)
        recycler = OrderRecycler()
        binding.orderParentRecycler.apply {
            adapter = recycler
            layoutManager = LinearLayoutManager(requireContext())
            theViewModel.ordersList.observe(viewLifecycleOwner){
                recycler.submitList(it)
            }
        }
    }


}