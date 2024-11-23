package com.example.quickmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productDetail = MutableStateFlow<Product?>(null)
    val productDetail: StateFlow<Product?> = _productDetail

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _cartState = MutableStateFlow<Result<Unit>?>(null)
    val cartState: StateFlow<Result<Unit>?> = _cartState

    /**
     * Cargar los detalles de un producto por ID.
     */
    fun loadProductDetails(productId: String) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null

            try {
                val product = repository.getProductById(productId)
                _productDetail.value = product ?: throw Exception("Producto no encontrado")
            } catch (e: Exception) {
                _errorState.value = e.message
            } finally {
                _loadingState.value = false
            }
        }
    }

    /**
     * Agregar producto al carrito (registrar compra).
     */
    fun addToCart(userId: String, productId: String) {
        viewModelScope.launch {
            if (userId.isBlank()) {
                _cartState.value = Result.failure(IllegalArgumentException("El userId no puede estar vacío."))
                return@launch
            }

            try {
                _cartState.value = repository.addToCart(userId, productId)
            } catch (e: Exception) {
                _cartState.value = Result.failure(e)
            }
        }
    }

    /**
     * Reiniciar el estado del carrito.
     */
    fun resetCartState() {
        _cartState.value = null
    }
}