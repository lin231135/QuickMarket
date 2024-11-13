package com.example.quickmarket.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.quickmarket.ui.theme.QuickMarketTheme
import com.example.quickmarket.viewmodel.AddProductViewModel
import com.example.quickmarket.viewmodel.HomeViewModel

class HomeActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val addProductViewModel: AddProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuickMarketTheme {
                HomeScreen(
                    onAddProductClick = {
                        startActivity(
                            Intent(this, AddProductActivity::class.java).apply {
                                putExtra("onProductAdded", true)
                            }
                        )
                    },
                    onProductClick = { product ->
                        val intent = Intent(this, ProductDetailActivity::class.java).apply {
                            putExtra("productId", product.id)
                        }
                        startActivity(intent)
                    },
                    onSettingsClick = { /* Acción para configuración */ },
                    homeViewModel = homeViewModel // Pasamos el ViewModel
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchProducts() // Recargamos los productos al volver al Home
    }
}
