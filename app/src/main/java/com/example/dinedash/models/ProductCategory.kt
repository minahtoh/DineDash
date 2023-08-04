package com.example.dinedash.models

data class ProductCategory(
    val products : List<Product>?,
    val type: String?,
    val picture: String?,
    var isExpandable : Boolean = false
)
