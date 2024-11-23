package com.example.quickmarket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quickmarket.ui.theme.QuickMarketTheme
import com.example.quickmarket.view.RegisterScreen
import com.example.quickmarket.viewmodel.*
import com.google.firebase.auth.FirebaseAuth
import com.example.quickmarket.data.repository.ProductRepository

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val addProductViewModel: AddProductViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val cartViewModelFactory = CartViewModelFactory(ProductRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMarketTheme {
                AppNavigation(
                    loginViewModel = loginViewModel,
                    registerViewModel = registerViewModel,
                    addProductViewModel = addProductViewModel,
                    homeViewModel = homeViewModel,
                    cartViewModelFactory = cartViewModelFactory
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    addProductViewModel: AddProductViewModel,
    homeViewModel: HomeViewModel,
    cartViewModelFactory: CartViewModelFactory
) {
    val navController = rememberNavController()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onContinueWithEmailClick = {
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                },
                loginViewModel = loginViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterComplete = { navController.navigate("home") { popUpTo("register") { inclusive = true } } },
                registerViewModel = registerViewModel
            )
        }
        composable("home") {
            HomeScreen(
                onAddProductClick = { navController.navigate("addProduct") },
                onProductClick = { product ->
                    navController.navigate("productDetail/${product.id}")
                },
                onMenuClick = { navController.navigate("cart") }, // Redirigir al carrito
                onSettingsClick = { /* Configuración futura */ },
                homeViewModel = homeViewModel
            )
        }
        composable("addProduct") {
            AddProductScreen(
                onProductAdded = { navController.popBackStack() },
                addProductViewModel = addProductViewModel,
                userId = userId,
                onMenuClick = { navController.navigate("cart") } // Redirigir al carrito
            )
        }
        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId").orEmpty()
            ProductDetailScreen(
                productId = productId,
                userId = userId,
                onMenuClick = { navController.navigate("cart") } // Redirigir al carrito
            )
        }
        composable("cart") {
            CartScreen(
                userId = userId,
                onBack = { navController.popBackStack() },
                viewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = cartViewModelFactory)
            )
        }
    }
}
