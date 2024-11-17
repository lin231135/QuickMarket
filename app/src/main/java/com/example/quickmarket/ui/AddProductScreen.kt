package com.example.quickmarket.ui

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.viewmodel.AddProductViewModel

@Composable
fun AddProductScreen(onProductAdded: () -> Unit, addProductViewModel: AddProductViewModel) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }

    val addProductResult by addProductViewModel.addProductResult.collectAsState()

    // Inicialización del launcher para seleccionar imágenes
    val selectImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it.toString()
        }
    }

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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Precio del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                selectImageLauncher.launch("image/*") // Abrir selector de imágenes
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar Imagen")
        }

        imageUri?.let { uri ->
            Text(text = "Imagen seleccionada: $uri")
            // Muestra la imagen seleccionada
            Image(
                painter = rememberImagePainter(data = uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Button(
            onClick = {
                val product = Product(
                    name = name,
                    price = price,
                    description = description,
                    imageUrl = imageUri ?: ""
                )
                addProductViewModel.addProduct(product)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Producto")
        }

        addProductResult?.exceptionOrNull()?.let { exception ->
            Text(
                text = "Error al agregar producto: ${exception.message}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun rememberSelectImageLauncher(onImageSelected: (String) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
    val activity = LocalContext.current as ComponentActivity
    return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            onImageSelected(it.toString())
        }
    }
}
