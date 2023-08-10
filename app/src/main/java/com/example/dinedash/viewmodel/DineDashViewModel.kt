package com.example.dinedash.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}