package com.example.dinedash.models


data class Order (
    val orderId: Long = 0,
    val totalPrice : Double = 0.0,
    val paymentDetails: PaymentDetails = PaymentDetails(),
    val productsBought: List<Product> = emptyList()
)
