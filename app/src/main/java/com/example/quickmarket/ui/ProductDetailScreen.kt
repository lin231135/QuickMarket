@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.quickmarket.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmarket.viewmodel.ProductDetailViewModel
import com.example.quickmarket.viewmodel.ProductDetailViewModelFactory
import com.example.quickmarket.data.repository.ProductRepository
import com.example.quickmarket.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModelFactory(ProductRepository()))
) {
    val product = viewModel.productDetail.collectAsState().value
    val isLoading = viewModel.loadingState.collectAsState().value
    val error = viewModel.errorState.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        viewModel.loadProductDetails(productId)
    }

    Scaffold(
        topBar = {
            ReusableTopBar(
                title = "Quick Market",
                onMenuClick = { /* Acción de ajustes */ },
                onSettingsClick = { /* Acción de ajustes */ }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error, color = Color.Red)
                }
            }
            product != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProductTitleAndRating(product.name)
                    ProductImage(imageUrl = product.imageUrl)
                    ProductPriceAndButton(product.price, snackbarHostState, coroutineScope)
                    ProductDetails(description = product.description)
                }
            }
        }
    }
}

@Composable
fun ProductTitleAndRating(name: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Estrella",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF008243)
                )
            }
            Text(text = "(5)", fontSize = 14.sp, color = Color(0xFF008243), modifier = Modifier.padding(start = 4.dp))
        }
    }
}

@Composable
fun ProductImage(imageUrl: String) {
    // Aquí puedes usar Coil o Glide si imageUrl es un enlace
    Image(
        painter = painterResource(id = R.drawable.baldor), // Usa un placeholder si es necesario
        contentDescription = "Imagen del producto",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProductPriceAndButton(price: String, snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Q$price",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Producto agregado al carrito",
                        actionLabel = "Deshacer"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        // El usuario hizo clic en "Deshacer"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
        ) {
            Text(text = "Agregar al carrito")
        }
    }
    Text(
        text = "Estado: Usado",
        fontSize = 14.sp,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun ProductDetails(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column {
            Text(
                text = "Descripción del producto",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Divider(
                color = Color(0xFF4CAF50),
                thickness = 2.dp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            text = description.ifBlank { "No hay descripción disponible." },
            fontSize = 14.sp,
        )
    }
}