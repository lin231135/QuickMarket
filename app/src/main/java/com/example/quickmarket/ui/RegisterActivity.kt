package com.example.quickmarket.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.quickmarket.view.RegisterScreen
import com.example.quickmarket.viewmodel.RegisterViewModel

class RegisterActivity : ComponentActivity() {
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(
                onLoginClick = {
                    startActivity(Intent(this, LoginActivity::class.java))
                },
                onRegisterComplete = {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                },
                registerViewModel = registerViewModel
            )
        }
    }
}