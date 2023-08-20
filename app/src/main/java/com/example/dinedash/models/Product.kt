package com.example.dinedash.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "productsTable")
data class Product(
    val productBrandName: String? = "",
    @PrimaryKey
    val productItemName: String = "",
    val productImage: String? = "",
    val productPrice: Long? = 0,
    val productRating: Double = 0.0,
    val numberLeft: Long? = 0,
    var quantity : Int = 1
): Serializable
