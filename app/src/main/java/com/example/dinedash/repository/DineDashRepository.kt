package com.example.dinedash.repository

import com.example.dinedash.db.DineDashDatabase
import com.example.dinedash.models.Product

class DineDashRepository(val database: DineDashDatabase) {

    fun getShoppingCartList() = database.getDao().getCartList()

    fun getTotalProductCount() = database.getDao().getTotalPrice()

    fun getPrices() = database.getDao().getListOfPrices()

    suspend fun insertProduct(product: Product) {
        database.getDao().insertToCart(product)
    }

    suspend fun removeProduct(product: Product){
        database.getDao().removeFromCart(product)
    }

    suspend fun deleteAll() = database.getDao().deleteAll()

}