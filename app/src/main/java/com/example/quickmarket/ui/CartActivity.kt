package com.example.quickmarket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmarket.ui.theme.QuickMarketTheme
import com.example.quickmarket.data.repository.ProductRepository
import com.example.quickmarket.viewmodel.CartViewModel
import com.example.quickmarket.viewmodel.CartViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class CartActivity : ComponentActivity() {
    private val cartViewModelFactory = CartViewModelFactory(ProductRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        setContent {
            QuickMarketTheme {
                CartScreen(
                    userId = userId,
                    onBack = { finish() },
                    viewModel = viewModel(factory = cartViewModelFactory)
                )
            }
        }
    }
}