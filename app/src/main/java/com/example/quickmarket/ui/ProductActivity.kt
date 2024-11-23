package com.example.quickmarket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.ui.theme.QuickMarketTheme

class ProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extraer datos del intent
        val productName = intent.getStringExtra("productName").orEmpty()
        val productPrice = intent.getStringExtra("productPrice").orEmpty()
        val productImageUrl = intent.getStringExtra("productImageUrl").orEmpty()

        // Crear un objeto Product
        val product = Product(productName, productPrice, productImageUrl)

        setContent {
            QuickMarketTheme {
                ProductScreen( // Composable unificado como ProductScreen
                    product = product
                )
            }
        }
    }
}