package com.example.dinedash.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dinedash.models.Product

@Dao
interface DineDashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToCart(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun removeFromCart(product: Product)

    @Query("DELETE FROM productsTable")
    suspend fun deleteAll()

    @Query("Select * from productsTable Order by `productItemName` Asc" )
    fun getCartList(): LiveData<List<Product>>
    @Query("Select * from productsTable Order by `productItemName` Asc" )
    fun returnCartList(): List<Product>

    @Query("Select COUNT(*) FROM productsTable")
    fun getTotalPrice():LiveData<Int>
    @Query("Select productPrice * quantity FROM productsTable")
    fun returnPrices(): List<Double>

    @Query("Select productPrice * quantity FROM productsTable")
    fun getListOfPrices(): LiveData<List<Double>>
    @Query("Select * from productsTable Where productItemName = :productName")
    suspend fun getProduct(productName:String): Product?
}