package com.example.dinedash.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinedash.models.Product
import com.example.dinedash.models.ProductType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DineDashViewModel: ViewModel() {
    private val TAG = "ViewModel"
    private val mFireStore = FirebaseFirestore.getInstance()
    private val _productCategory = MutableLiveData<List<ProductType>>()
    val productCategory : LiveData<List<ProductType>> = _productCategory

    fun getProductCategory(fragment: Fragment){
        viewModelScope.launch {
            try {
                val snapshot = mFireStore.collection("warehouse").get().await()
                val productList = mutableListOf<ProductType>()
                for (document in snapshot){
                    val productType = document.toObject(ProductType::class.java)
                    productList.add(productType)
                }
                _productCategory.value = productList

            }catch (e:Exception){
                Log.e(TAG, "getProductCategory: $e")
                Toast.makeText(fragment.requireContext(), "Error $e occurred", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    fun uploadGoods(fragment: Fragment){
        val productList = listOf(
            Product(
                "",
                "Men's Casual Shoes Soft Leather, Soft Sole, Breathable Shoes -Brown",
                "",
                5300,
                5.0,
                9
            ),
            Product(
                "A4 Fashion",
                "Business Soft-soled Plaid Leather Shoes-P8822 Black",
                "",
                23400,
                0.0,
                21
            ),
        Product(
                "Depally",
                "STONE DESIGNERS WEDDING SHOE BLACK",
                "",
                17805,
                5.0,
                2
            ),
        Product(
                "Santiago",
                "2022 Mens Executive Business Office Casual Event Leather Formal Shoes Black",
                "",
                1750549,
                5.0,
                5
            ),
        Product(
                "Varrati",
            "Mens Executive Shoes Italian Leather Shoes Coffee Brown",
                "",
                11000,
                0.0,
                12
            ),
        Product(
                "FOLLETEL",
                "2021 Men Office Oxford Dress Patterned Shoe F8 - Blue",
                "",
                11510,
                0.0,
                15
            ),
        Product(
                "",
                "Breathable Trend Running Shoes-TL605 White",
                "",
                4500,
                4.5,
                106
            ),/*
        Product(
                "DELL",
                "XPS 13 PLUS 9320,CORE I7-1260P,2TB SSD/32GB RAM,13.4\" OLED TOUCHSCREEN,BACKLIT,FINGERPRINT,WIN 11",
                "",
                1550000,
                0.0,
                2
            ),
        Product(
                "DELL",
                "ALIENWARE M16 R1,CORE I9-13900HX,1TB SSD/32GB RAM,12GB RTX 4080 GRAPHICS,16\" QHD+ DISPLAY,WIN 11",
                "",
                4090000,
                0.0,
                2
            ),
        Product(
                "Acer",
                "TRAVELMATE B3 TMB311 CELERON N4020 4GB RAM 64GB HDD",
                "",
                140850,
                0.0,
                4
            )*/
        )

        for (product in productList) {
            val productMap = mapOf(
                "productBrandName" to product.productBrandName,
                "productItemName" to product.productItemName,
                "productPrice" to product.productPrice,
                "productImage" to product.productImage,
                "productRating" to product.productRating,
                "numberLeft" to product.numberLeft,
            )

            mFireStore.collection("/warehouse/Shoes/products").add(productMap)
                .addOnSuccessListener {
                    Toast.makeText(fragment.requireContext(), "Successfully uploaded $it", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                    Toast.makeText(fragment.requireContext(), "Failed to upload, $it occurred", Toast.LENGTH_SHORT)
                        .show()
                }
        }



    }
}