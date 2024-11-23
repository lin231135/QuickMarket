package com.example.quickmarket.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.viewmodel.AddProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    onProductAdded: () -> Unit,
    addProductViewModel: AddProductViewModel,
    userId: String, // Parámetro obligatorio para agregar el producto
    onMenuClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    if (userId.isBlank()) {
        throw IllegalArgumentException("El userId no puede estar vacío. Asegúrate de pasarlo correctamente.")
    }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    val addProductResult by addProductViewModel.addProductResult.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(addProductResult) {
        addProductResult?.let { result ->
            if (result.isSuccess) {
                addProductViewModel.resetAddProductResult()
                onProductAdded()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Barra superior reutilizable
        ReusableTopBar(
            title = "Agregar Producto",
            onMenuClick = onMenuClick,
            onSettingsClick = onSettingsClick
        )

        // Contenido desplazable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo para el nombre del producto
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del Producto", color = Color(0xFF008d36)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF83c88d),
                    cursorColor = Color(0xFF008d36),
                    containerColor = Color(0xFFF1F8F5)
                )
            )

            // Campo para el precio del producto
            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio del Producto", color = Color(0xFF008d36)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF83c88d),
                    cursorColor = Color(0xFF008d36),
                    containerColor = Color(0xFFF1F8F5)
                )
            )

            // Campo para la descripción del producto
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción del Producto", color = Color(0xFF008d36)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF83c88d),
                    cursorColor = Color(0xFF008d36),
                    containerColor = Color(0xFFF1F8F5)
                )
            )

            // Campo para la URL de la imagen del producto
            TextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL de la Imagen del Producto", color = Color(0xFF008d36)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF83c88d),
                    cursorColor = Color(0xFF008d36),
                    containerColor = Color(0xFFF1F8F5)
                )
            )

            // Vista previa de la imagen si la URL es válida
            imageUrl.takeIf { it.isNotBlank() }?.let { url ->
                Text(
                    text = "Vista previa de la imagen:",
                    color = Color(0xFF008d36)
                )
                Image(
                    painter = rememberImagePainter(data = url),
                    contentDescription = "Imagen del producto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            // Botón para agregar el producto
            Button(
                onClick = {
                    val product = Product(
                        name = name,
                        price = price,
                        description = description,
                        imageUrl = imageUrl
                    )
                    addProductViewModel.addProduct(userId, product)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008d36),
                    contentColor = Color.White
                )
            ) {
                Text("Agregar Producto")
            }

            // Mostrar error si ocurre algún problema al agregar el producto
            addProductResult?.exceptionOrNull()?.let { exception ->
                Text(
                    text = "Error al agregar producto: ${exception.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
