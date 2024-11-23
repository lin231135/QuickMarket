package com.example.quickmarket.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.data.repository.ProductRepository
import com.example.quickmarket.viewmodel.CartViewModel
import com.example.quickmarket.viewmodel.CartViewModelFactory

@Composable
fun CartScreen(
    userId: String,
    onBack: () -> Unit,
    viewModel: CartViewModel = viewModel(factory = CartViewModelFactory(ProductRepository()))
) {
    val cartProducts = viewModel.cartProducts.collectAsState().value
    val isLoading = viewModel.loadingState.collectAsState().value
    val error = viewModel.errorState.collectAsState().value

    Scaffold(
        topBar = {
            ReusableTopBar(
                title = "Carrito de Compras",
                onMenuClick = onBack // Opcional: volver atrás
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = error, color = MaterialTheme.colorScheme.error)
                    }
                }
                cartProducts.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cartProducts) { product ->
                            CartProductItem(product = product)
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No hay productos en el carrito.")
                    }
                }
            }
        }
    }
}

@Composable
fun CartProductItem(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Precio: Q${product.price}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description.ifBlank { "Sin descripción" },
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
