package com.example.quickmarket.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quickmarket.ui.theme.QuickMarketTheme
import com.google.firebase.auth.FirebaseAuth

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getStringExtra("productId") ?: ""
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        setContent {
            QuickMarketTheme {
                ProductDetailScreen(
                    productId = productId,
                    userId = userId,
                    onMenuClick = {
                        // Acción para redirigir al carrito
                        startActivity(Intent(this, CartActivity::class.java))
                    }
                )
            }
        }
    }
}