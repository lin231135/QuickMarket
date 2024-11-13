package com.example.quickmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.data.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val productRepository = ProductRepository()

    // Lista completa de productos
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    // Texto de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    // Productos filtrados en base al texto de búsqueda
    val filteredProducts: StateFlow<List<Product>> = _searchQuery
        .combine(_products) { query, products ->
            if (query.isEmpty()) products
            else products.filter {
                it.name.contains(query, ignoreCase = true) || // Busca en el nombre
                        it.description.contains(query, ignoreCase = true) // Busca en la descripción
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        fetchProducts()
    }

    // Método público para recargar productos
    fun fetchProducts() {
        viewModelScope.launch {
            val fetchedProducts = productRepository.getProducts()
            _products.value = fetchedProducts
        }
    }

    // Actualizar el texto de búsqueda
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
