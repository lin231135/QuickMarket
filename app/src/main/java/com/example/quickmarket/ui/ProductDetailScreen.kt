package com.example.quickmarket.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmarket.viewmodel.ProductDetailViewModel
import com.example.quickmarket.viewmodel.ProductDetailViewModelFactory
import com.example.quickmarket.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun ProductDetailScreen(
    productId: String,
    userId: String,
    onMenuClick: () -> Unit,
    viewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModelFactory(ProductRepository()))
) {
    val product = viewModel.productDetail.collectAsState().value
    val isLoading = viewModel.loadingState.collectAsState().value
    val error = viewModel.errorState.collectAsState().value
    val cartState = viewModel.cartState.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        viewModel.loadProductDetails(productId)
    }

    LaunchedEffect(cartState) {
        cartState?.let { result ->
            if (result.isSuccess) {
                snackbarHostState.showSnackbar("Producto agregado al carrito.")
                viewModel.resetCartState()
            } else {
                snackbarHostState.showSnackbar("Error al agregar al carrito: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    Scaffold(
        topBar = {
            ReusableTopBar(
                title = "Detalles del Producto",
                onMenuClick = onMenuClick,
                onSettingsClick = { /* Acción de ajustes */ }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
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
                    ProductPriceAndButton(product.price) {
                        viewModel.addToCart(userId, productId)
                    }
                    ProductDetails(description = product.description)
                }
            }
        }
    }
}

@Composable
fun ProductTitleAndRating(name: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductImage(imageUrl: String) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageUrl) {
        imageBitmap = loadImageFromUrl(imageUrl)
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap!!,
            contentDescription = "Imagen del producto",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Cargando imagen...", color = Color.Gray)
        }
    }
}

suspend fun loadImageFromUrl(imageUrl: String): ImageBitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(imageUrl)
            val bitmap = BitmapFactory.decodeStream(url.openStream())
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

@Composable
fun ProductPriceAndButton(price: String, onAddToCartClick: () -> Unit) {
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
            onClick = onAddToCartClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50), // Color principal
                contentColor = Color.White // Color del texto dentro del botón
            )
        ) {
            Text("Agregar al carrito")
        }
    }
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
