package com.example.dinedash.repository

import com.example.dinedash.db.DineDashDatabase
import com.example.dinedash.models.Product

class DineDashRepository(private val database: DineDashDatabase) {

    fun getShoppingCartList() = database.getDao().getCartList()
    fun returnShoppingCartList() = database.getDao().returnCartList()

    fun getTotalProductCount() = database.getDao().getTotalPrice()

    fun getPrices() = database.getDao().getListOfPrices()
    fun returnPrices() = database.getDao().returnPrices()

    suspend fun increaseProductQuantity(productName:String){
        val product = database.getDao().getProduct(productName)
        if (product != null && product.numberLeft!! > product.quantity){
            val newProduct = product.copy(quantity = product.quantity + 1 )
            database.getDao().updateProduct(newProduct)
        }
    }

    suspend fun decreaseProductQuantity(productName:String){
        val product = database.getDao().getProduct(productName)
        if (product != null ){
            val newProduct = product.copy(quantity = product.quantity - 1 )
            database.getDao().updateProduct(newProduct)
        }
    }

    suspend fun insertProduct(product: Product) {
        database.getDao().insertToCart(product)
    }

    suspend fun removeProduct(product: Product){
        database.getDao().removeFromCart(product)
    }

    suspend fun deleteAll() = database.getDao().deleteAll()

}