package com.example.quickmarket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quickmarket.ui.theme.QuickMarketTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getStringExtra("productId") ?: ""

        setContent {
            QuickMarketTheme {
                ProductDetailScreen(productId = productId)
            }
        }
    }
}
