package com.example.quickmarket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmarket.ui.theme.QuickMarketTheme
import com.google.firebase.auth.FirebaseAuth

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el userId desde el Intent o Firebase Authentication
        val userId = intent.getStringExtra("userId") ?: FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Verificar si el userId es válido antes de iniciar la pantalla
        if (userId.isBlank()) {
            throw IllegalArgumentException("El userId no puede estar vacío. Asegúrate de que el usuario esté autenticado.")
        }

        setContent {
            QuickMarketTheme {
                AddProductScreen(
                    onProductAdded = { finish() },
                    addProductViewModel = viewModel(),
                    userId = userId
                )
            }
        }
    }
}
