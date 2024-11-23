package com.example.quickmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val productRepository = ProductRepository()

    // Flujo para manejar la lista de productos
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    // Estado para manejar errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        loadProducts()
    }

    // Función para cargar productos desde Firebase
    fun loadProducts() {
        viewModelScope.launch {
            try {
                val productList = productRepository.getProducts()
                _products.value = productList
            } catch (e: Exception) {
                _error.value = "Error loading products: ${e.message}"
            }
        }
    }
}