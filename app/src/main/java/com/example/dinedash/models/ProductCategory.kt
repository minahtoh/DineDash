package com.example.dinedash.models

data class ProductCategory(
    val productID: String? = "",
    val productImage: String? = "",
    val productAvailable : List<Product>?,
    var isExpandable : Boolean = false
){
    fun getProductCategoryByID(products: List<ProductCategory>, productID: String): ProductCategory? {
        return products.find { it.productID == productID }
    }
}
