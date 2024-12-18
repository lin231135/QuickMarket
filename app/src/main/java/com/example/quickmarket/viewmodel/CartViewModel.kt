package com.example.quickmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> get() = _cartProducts

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    fun loadCartProducts(userId: String) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            try {
                val products = repository.getPurchasedProducts(userId)
                _cartProducts.value = products
            } catch (e: Exception) {
                _errorState.value = "Error al cargar el carrito: ${e.message}"
            } finally {
                _loadingState.value = false
            }
        }
    }
}