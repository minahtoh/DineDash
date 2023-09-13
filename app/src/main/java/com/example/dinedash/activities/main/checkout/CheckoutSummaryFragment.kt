package com.example.dinedash.activities.main.checkout

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dinedash.R
import com.example.dinedash.databinding.FragmentCheckoutSummaryBinding
import com.example.dinedash.utils.Constants.DELIVERY_FEE
import com.example.dinedash.utils.DineDashProgressBar
import com.example.dinedash.viewmodel.DineDashViewModel
import com.example.dinedash.viewmodel.LoadingState
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutSummaryFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutSummaryBinding
    private val theViewModel: DineDashViewModel by activityViewModels()
    private val CHANNEL_ID = "dinedash_id_01"
    private val notificationId = 101
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
        createNotificationChannel()
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
                theViewModel.submissionLoadingState.observe(viewLifecycleOwner){
                    when(it){
                        LoadingState.LOADING -> {
                            DineDashProgressBar.show(requireContext())
                        }
                        LoadingState.SUCCESSFUL -> {
                            DineDashProgressBar.hide()
                            theViewModel.clearCart()
                            sendNotification()
                            val action = CheckoutFragmentDirections.actionCheckoutFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }

                        else -> {
                            DineDashProgressBar.hide()
                        }
                    }
                }
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
            theViewModel.uploadGoods(this)

        }
        .show()
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val name = "Notifications"
            val descriptionText = "Order Submitted!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val builder = NotificationCompat.Builder(requireContext(),CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Order Submitted!")
            .setContentText("Your order has been successfully submitted")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(requireContext())){
            notify( notificationId, builder.build())
        }

    }

}