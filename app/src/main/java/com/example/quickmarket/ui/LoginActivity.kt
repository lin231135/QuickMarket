package com.example.quickmarket.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.quickmarket.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onRegisterClick = {
                    startActivity(Intent(this, RegisterActivity::class.java))
                },
                onContinueWithEmailClick = {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                },
                loginViewModel = loginViewModel
            )
        }
    }
}
