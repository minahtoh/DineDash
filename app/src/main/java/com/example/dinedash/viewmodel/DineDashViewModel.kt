package com.example.dinedash.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinedash.models.Product
import com.example.dinedash.models.ProductCategory
import com.example.dinedash.models.ProductType
import com.example.dinedash.repository.DineDashRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class LoadingState{ LOADING, SUCCESSFUL, ERROR }
class DineDashViewModel(private val repository: DineDashRepository): ViewModel() {
    private val TAG = "ViewModel"
    private val mFireStore = FirebaseFirestore.getInstance()
    private val _productCategory = MutableLiveData<List<ProductCategory>>()
    val productCategory : LiveData<List<ProductCategory>> = _productCategory
    private val _searchResults = MutableLiveData<List<ProductCategory>?>()
    val searchResults : MutableLiveData<List<ProductCategory>?> = _searchResults
    private val _homeLoadingState = MutableLiveData<LoadingState>()
    val homeLoadingState : LiveData<LoadingState> = _homeLoadingState

    fun getProductCategory(fragment: Fragment){
        viewModelScope.launch {
            _homeLoadingState.postValue(LoadingState.LOADING)

            try {
                val snapshot = mFireStore.collection("warehouse").get().await()
                val productList = mutableListOf<ProductCategory>()
                for (document in snapshot){
                    val productType = document.toObject(ProductType::class.java)
                    val products = document.reference.collection("products").get().await().toObjects(Product::class.java)
                    val productsType = ProductCategory(
                        document.getString("productID"),
                        document.getString("productImage"),
                        products
                    )
                    productList.add(productsType)
                }
                _productCategory.value = productList

                _homeLoadingState.postValue(LoadingState.SUCCESSFUL)
            }catch (e:Exception){
                Log.e(TAG, "getProductCategory: $e")
                Toast.makeText(fragment.requireContext(), "Error $e occurred", Toast.LENGTH_SHORT)
                    .show()
                _homeLoadingState.postValue(LoadingState.ERROR)
            }

        }
    }

    fun addToCart(product: Product, fragment: Fragment){
        viewModelScope.launch {
            try {
                repository.insertProduct(product)
                Toast.makeText(fragment.requireContext(),
                    "Adding ${product.quantity} ${product.productItemName} to Cart!",Toast.LENGTH_SHORT)
                    .show()
            }catch (e:Exception){
                Toast.makeText(fragment.requireContext(),"Exception $e occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun removeFromCart(product: Product, fragment: Fragment){
        viewModelScope.launch {
            try {
                repository.removeProduct(product)
            }catch (e:Exception){
                Toast.makeText(fragment.requireContext(),"Exception $e occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            try {
                repository.deleteAll()
            }   catch (e:Exception){
                Log.e(TAG, "clearCart: $e occurred")
            }
        }
    }

    fun getShoppingList() = repository.getShoppingCartList()
    fun getProductCount() = repository.getTotalProductCount()
    fun getPricesList() = repository.getPrices()

    fun increaseQuantity(product: Product){
        viewModelScope.launch {
            repository.increaseProductQuantity(product.productItemName)
        }
    }

    fun reduceQuantity(product: Product){
        viewModelScope.launch {
            repository.decreaseProductQuantity(product.productItemName)
        }
    }

    fun searchForProduct(text : String?){
            val filteredProduct = if (text.isNullOrEmpty()){
                return
            }
        else{
                _productCategory.value?.filter { product->
                        product.productAvailable!!.any { nameValue->
                            nameValue.productItemName.contains(text, ignoreCase = true)
                                    || nameValue.productBrandName?.contains(text, ignoreCase = true) ?: false
                        }
                }
        }

        _searchResults.postValue(filteredProduct)
    }
}


    class  DineDashViewModelFactory(private val repository: DineDashRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DineDashViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DineDashViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }


        /*
        fun uploadGoods(fragment: Fragment){
            val productList = listOf(

                Product(
                    "WEYON",
                    "32 Inches LED TV (32WAN) - Black +1 Year Warranty",
                    "",
                    63300,
                    0.0,
                    9
                ),
                Product(
                    "Amani",
                    "32 Inches LED TV Amani @promo Price + Free Gift Inside\n",
                    "",
                    59400,
                    0.0,
                    1
                ),
            Product(
                    "Hisense",
                    "32 Inches FHD LED TV (A5100) - Black +1 Year Warranty",
                    "",
                    77805,
                    5.0,
                    2
                ),
            Product(
                    "LG",
                    "32 Inch HD LED TV + Wall Hanger {2 Year Warranty}",
                    "",
                    70549,
                    5.0,
                    5
                ),
            Product(
                    "Skyrun",
                "32 Inches LED HD TV (32XM/N68D) - Black + 1 Year Warranty",
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
                ),
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
                )
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

                mFireStore.collection("/warehouse/Television/products").add(productMap)
                    .addOnSuccessListener {
                        Toast.makeText(fragment.requireContext(), "Successfully uploaded $it", Toast.LENGTH_SHORT)
                            .show()
                    }.addOnFailureListener {
                        Toast.makeText(fragment.requireContext(), "Failed to upload, $it occurred", Toast.LENGTH_SHORT)
                            .show()
                    }
            }

        }*/
}