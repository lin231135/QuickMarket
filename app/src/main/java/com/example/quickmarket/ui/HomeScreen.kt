package com.example.quickmarket.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.quickmarket.R
import com.example.quickmarket.data.model.Product
import com.example.quickmarket.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun HomeScreen(
    onAddProductClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onMenuClick: () -> Unit,
    onSettingsClick: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val searchText by homeViewModel.searchQuery.collectAsState()
    val filteredProducts by homeViewModel.filteredProducts.collectAsState()
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            Column {
                ReusableTopBar(
                    title = "Quick Market",
                    onMenuClick = onMenuClick,
                    onSettingsClick = onSettingsClick
                )
                SearchBar(
                    searchText = searchText,
                    onSearchTextChanged = { homeViewModel.updateSearchQuery(it) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProductClick,
                containerColor = Color(0xFF83c88d),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Product"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFc8e4d6))
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { CarouselSection() }
            items(filteredProducts.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowProducts.forEach { product ->
                        ProductCard(product, onProductClick)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color(0xFF008d36),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
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
        if (searchText.isNotEmpty()) {
            IconButton(
                onClick = {
                    onSearchTextChanged("") // Limpia el texto de búsqueda
                    focusManager.clearFocus() // Quita el enfoque del campo de texto
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Clear search",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CarouselSection() {
    val images = listOf(
        R.drawable.add1,
        R.drawable.add2,
        R.drawable.add3
    )
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex = (currentIndex + 1) % images.size
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = images[currentIndex]),
            contentDescription = "Carousel Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(9.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (index == currentIndex) Color(0xFF83c88d) else Color.Gray)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onProductClick: (Product) -> Unit) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
            .clickable { onProductClick(product) }
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cargar la imagen desde la URL usando Coil
        Image(
            painter = rememberImagePainter(
                data = product.imageUrl, // URL de la imagen del producto
                builder = {
                    crossfade(true) // Añadir transición
                    placeholder(R.drawable.logo) // Imagen de reserva
                    error(R.drawable.logo) // Imagen en caso de error
                }
            ),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = product.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(text = "Q${product.price}", color = Color.Gray, fontSize = 14.sp)
    }
}