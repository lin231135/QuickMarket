@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.quickmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmarket.ui.theme.QuickMarketTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductViewScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMarketTheme {
                ProductDetailScreen(productId = null)
            }
        }
    }
}

@Composable
fun ProductDetailScreen(productId: String?) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
                    ) {
                        IconButton(onClick = { /* Handle menu click */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = "Menu",
                                tint = Color(0xFF83c88d)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.carrito),
                                contentDescription = "Logo",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Quick Market",
                                fontSize = 22.sp,
                                style = TextStyle(
                                    color = Color(0xFF008d36),
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { /* Handle settings click */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.settings),
                                contentDescription = "Settings",
                                tint = Color(0xFF83c88d)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SearchBar(searchText = "", onSearchTextChanged = {})
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProductTitleAndRating()
                ProductImage()
                ProductPriceAndButton(snackbarHostState, coroutineScope)
                ProductDetails()
            }
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF008d36), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Search",
            tint = Color(0xFF008d36),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                if (searchText.isEmpty()) {
                    Text(
                        text = "Buscar productos...",
                        style = TextStyle(color = Color(0xFF008d36), fontSize = 16.sp)
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun ProductTitleAndRating() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Álgebra de Baldor (2020)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Estrella",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF008243)
                )
            }
            Text(text = "(5)", fontSize = 14.sp, color = Color(0xFF008243), modifier = Modifier.padding(start = 4.dp))
        }
    }
}

@Composable
fun ProductImage() {
    Image(
        painter = painterResource(id = R.drawable.baldor),
        contentDescription = "Imagen del producto",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProductPriceAndButton(snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Q200.00",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Producto agregado al carrito",
                        actionLabel = "Deshacer"
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        // El usuario hizo clic en "Deshacer"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
        ) {
            Text(text = "Agregar al carrito")
        }
    }
    Text(
        text = "Estado: Usado",
        fontSize = 14.sp,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun ProductDetails() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column {
            Text(
                text = "Descripción del producto",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Divider(
                color = Color(0xFF4CAF50),
                thickness = 2.dp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            text = """
                Autor: Aurelio Baldor
                Editorial: PATRIA
                Año: 2020
                Páginas: 584
                Encuadernación: Tapa dura
                Idioma: Español
            """.trimIndent(),
            fontSize = 14.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickMarketTheme {
        ProductDetailScreen(productId = null)
    }
}