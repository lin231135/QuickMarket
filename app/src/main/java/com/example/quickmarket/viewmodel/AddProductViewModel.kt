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

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                val imageUrl = if (product.imageUrl.isNotBlank()) {
                    productRepository.uploadImage(product.imageUrl)
                } else {
                    ""
                }

                val updatedProduct = product.copy(imageUrl = imageUrl)
                productRepository.addProduct(updatedProduct)
                _addProductResult.value = Result.success(Unit)
            } catch (e: Exception) {
                _addProductResult.value = Result.failure(e)
            }
        }
    }

    fun resetAddProductResult() {
        _addProductResult.value=null
        }
}