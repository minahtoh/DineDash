package com.example.dinedash.models

data class ProductCategory(
    val productID: String? = "",
    val productImage: String? = "",
    val productAvailable : List<Product>?,
    var isExpandable : Boolean = false
)
