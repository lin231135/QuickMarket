package com.example.quickmarket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmarket.ui.theme.QuickMarketTheme
import com.example.quickmarket.viewmodel.AddProductViewModel

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMarketTheme {
                AddProductScreen(onProductAdded = { finish() }, addProductViewModel = viewModel())
            }
        }
    }
}
