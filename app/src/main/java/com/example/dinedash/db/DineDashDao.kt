package com.example.dinedash.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dinedash.models.Product

@Dao
interface DineDashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToCart(product: Product)

    @Delete
    suspend fun removeFromCart(product: Product)

    @Query("DELETE FROM productsTable")
    suspend fun deleteAll()

    @Query("Select * from productsTable Order by `productItemName` Asc" )
    fun getCartList(): LiveData<List<Product>>

    @Query("Select COUNT(*) FROM productsTable")
    fun getTotalPrice():LiveData<Int>

    @Query("Select productPrice * quantity FROM productsTable")
    fun getListOfPrices(): LiveData<List<Double>>
}