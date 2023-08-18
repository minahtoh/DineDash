package com.example.dinedash.models

import java.io.Serializable

data class Product(
    val productBrandName: String? = "",
    val productItemName: String? = "",
    val productImage: String? = "",
    val productPrice: Long? = 0,
    val productRating: Double = 0.0,
    val numberLeft: Long? = 0
): Serializable
