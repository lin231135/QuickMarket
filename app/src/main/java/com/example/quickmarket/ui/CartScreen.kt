package com.example.quickmarket.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
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
    LaunchedEffect(userId) {
        viewModel.loadCartProducts(userId)
    }

    val cartProducts = viewModel.cartProducts.collectAsState().value
    val isLoading = viewModel.loadingState.collectAsState().value
    val error = viewModel.errorState.collectAsState().value

    Scaffold(
        topBar = {
            ReusableTopBar(
                title = "Carrito de Compras",
                onMenuClick = onBack
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
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, color = Color(0xFF008d36), shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .size(80.dp)
                    .border(1.dp, color = Color(0xFF008d36), shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
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
}