package com.example.dinedash.models

data class PaymentDetails(
    val name: String,
    val number: String,
    val expiryDate:String,
    val cvv: String
)
