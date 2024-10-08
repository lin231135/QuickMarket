@file:OptIn(ExperimentalMaterial3Api::class)

import com.example.quickmarket.R

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(onSettingsClick: () -> Unit, onSearchClick: () -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(true) }
    val scrollState = rememberLazyListState()

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex > 0 }
            .collect { showSearchBar = !it }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                        ) {
                            IconButton(onClick = { /* Handle menu click */ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu),
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
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Quick Market",
                                    fontSize = 22.sp,
                                    style = TextStyle(
                                        color = Color(0xFF008d36),
                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(onClick = onSettingsClick) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_settings),
                                    contentDescription = "Settings",
                                    tint = Color(0xFF83c88d)
                                )
                            }
                        }
                    }
                )

                if (showSearchBar) {
                    SearchBar(searchText, onSearchTextChanged = {
                        searchText = it
                        if (it.isNotEmpty()) {
                            onSearchClick()
                        }
                    })
                }
            }
        }
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFc8e4d6))
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(100.dp)) }
            item { CarouselSection() }
            item { CategoriesSection() }
            items(getProducts().chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowProducts.forEach { product ->
                        ProductCard(product)
                    }
                }
            }
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
            painter = painterResource(id = R.drawable.ic_search),
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
fun CategoriesSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        CategoryItem("Libros", R.drawable.ic_book)
        CategoryItem("Tutoriales", R.drawable.ic_tutorial)
        CategoryItem("Clubes", R.drawable.ic_clubs)
    }
}

@Composable
fun CategoryItem(category: String, iconId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = category,
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(48.dp)
        )
        Text(text = category)
    }
}

@Composable
fun ProductCard(product: Product) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.imageId),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = product.name, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
        Text(text = product.price, color = Color.Gray, fontSize = 14.sp)
    }
}

fun getProducts(): List<Product> {
    return listOf(
        Product("Pachones", "Q15.00", R.drawable.pachones),
        Product("Cálculo Libro", "Q40.00", R.drawable.calculo),
        Product("Bicicleta", "Q750.00", R.drawable.bici),
        Product("Florero", "Q20.00", R.drawable.florero),
        Product("Laptop", "Q5000.00", R.drawable.imagen),
    )
}

data class Product(val name: String, val price: String, val imageId: Int)