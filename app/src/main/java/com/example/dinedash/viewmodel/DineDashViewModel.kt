package com.example.dinedash.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinedash.models.Order
import com.example.dinedash.models.PaymentDetails
import com.example.dinedash.models.Product
import com.example.dinedash.models.ProductCategory
import com.example.dinedash.models.ProductType
import com.example.dinedash.repository.DineDashRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
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
    val paymentDetails = MutableLiveData<PaymentDetails>()
    private val _ordersList = MutableLiveData<List<Order>>()
    val ordersList : LiveData<List<Order>> = _ordersList
    private val _orderLoadingState = MutableLiveData<LoadingState>()
    val orderLoadingState : LiveData<LoadingState> = _orderLoadingState
    private val _submissionLoadingState = MutableLiveData<LoadingState>()
    val submissionLoadingState : LiveData<LoadingState> = _submissionLoadingState

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
    fun uploadGoods(fragment: Fragment){
        viewModelScope.launch(Dispatchers.IO) {
            _submissionLoadingState.postValue(LoadingState.LOADING)
            val productList = repository.returnShoppingCartList()
            val orderCost = repository.returnPrices().sum()
            val paymentDetails = paymentDetails.value!!
            val orderTime = System.currentTimeMillis()
            val orderTaken = Order(orderTime,orderCost,paymentDetails,productList)

            mFireStore.collection("/Orders").add(orderTaken)
                    .addOnSuccessListener {
                            Toast.makeText(fragment.requireContext(), "Your Order has been successfully submitted!", Toast.LENGTH_SHORT)
                                .show()
                            _submissionLoadingState.postValue(LoadingState.SUCCESSFUL)
                    }.addOnFailureListener {
                            Toast.makeText(fragment.requireContext(), "Failed to submit order, $it occurred", Toast.LENGTH_SHORT)
                                .show()
                            _submissionLoadingState.postValue(LoadingState.SUCCESSFUL)
                    }

        }
    }

    fun getOrdersList(fragment: Fragment){
        viewModelScope.launch {
            _orderLoadingState.postValue(LoadingState.LOADING)
            try {
                val list = mFireStore.collection("Orders").get().await().toObjects(Order::class.java)
                _ordersList.postValue(list)
                _orderLoadingState.postValue(LoadingState.SUCCESSFUL)
            }catch (e:Exception){
                Log.e(TAG, "getOrdersList: $e")
                _orderLoadingState.postValue(LoadingState.ERROR)
                Toast.makeText(fragment.requireContext(),"Error $e occurred",Toast.LENGTH_SHORT).show()
            }
        }
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

}