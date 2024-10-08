package com.example.quickmarket

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickmarket.ui.theme.QuickMarketTheme
import com.uvg.vistaproyppmchet.SearchViewScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMarketTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onContinueWithEmailClick = { navController.navigate("home") }
            )
        }
        composable("register") {
            RegisterScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterComplete = { navController.navigate("home") }
            )
        }
        composable("home") {
            HomeScreen(
                onSettingsClick = { navController.navigate("settings") },
                onSearchClick = { navController.navigate("search") }
            )
        }
        composable("search") {
            SearchViewScreen(onLogoutClick = { navController.navigate("home") }, onProductClick = { productId ->
                navController.navigate("product/$productId")
            })
        }
        composable("settings") {
            SettingsScreen(onLogoutClick = { navController.navigate("home") })
        }
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(productId = productId)
        }
    }
}