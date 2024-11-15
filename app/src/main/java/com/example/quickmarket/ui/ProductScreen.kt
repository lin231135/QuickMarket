package com.example.quickmarket.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmarket.R
import com.example.quickmarket.data.model.Product

@Composable
fun ProductScreen(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = product.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.baldor), // Asegúrate de tener un recurso con este ID
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = product.price,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Detalles del producto: información adicional sobre este producto.",
            style = MaterialTheme.typography.bodyMedium
            }
}