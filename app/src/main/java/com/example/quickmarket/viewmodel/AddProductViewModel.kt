package com.example.quickmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddProductViewModel : ViewModel() {
    private val productRepository = ProductRepository()

    private val _addProductResult = MutableStateFlow<Result<Unit>?>(null)
    val addProductResult: StateFlow<Result<Unit>?> get() = _addProductResult

    fun addProduct(userId: String, product: Product) {
        viewModelScope.launch {
            if (userId.isBlank()) {
                _addProductResult.value = Result.failure(IllegalArgumentException("El userId no puede estar vacío."))
                return@launch
            }

            try {
                val updatedProduct = product.copy() // No se procesa imageUrl porque ya es una URL
                _addProductResult.value = productRepository.addProductWithUser(userId, updatedProduct)
            } catch (e: Exception) {
                _addProductResult.value = Result.failure(e)
            }
        }
    }

    fun resetAddProductResult() {
        _addProductResult.value = null
    }
}
