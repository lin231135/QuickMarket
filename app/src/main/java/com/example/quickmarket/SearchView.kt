package com.uvg.vistaproyppmchet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.quickmarket.Book
import com.example.quickmarket.R
import com.example.quickmarket.bookList
import com.example.quickmarket.ui.theme.QuickMarketTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewScreen(onLogoutClick: () -> Unit, onProductClick: (String) -> Unit) {
    Scaffold(
        topBar = {
            Encabezado(onLogoutClick = onLogoutClick)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SearchAndSortBar()
            BookList(books = bookList) { book ->
                onProductClick(book.id)
            }
        }
    }
}

@Composable
fun SearchAndSortBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFc9e5d7))
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = { Text(stringResource(R.string.busca_tu_producto)) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                IconButton(onClick = { /**/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.lupa),
                        contentDescription = stringResource(R.string.buscar),
                        tint = Color(0xFF008243)
                    )
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { /**/ },
                colors = ButtonDefaults.buttonColors(Color(0xFF008243)),
                modifier = Modifier.height(35.dp)
            ) {
                Text(stringResource(R.string.ordenar))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { /**/ },
                colors = ButtonDefaults.buttonColors(Color(0xFF008243)),
                modifier = Modifier.height(35.dp)
            ) {
                Text(stringResource(R.string.filtrar))
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onItemClick: (Book) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = Color.White)
            .clickable { onItemClick(book) },
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = book.image),
                contentDescription = stringResource(R.string.book_image),
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
                    .background(color = Color.White),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(book.title, style = MaterialTheme.typography.titleLarge)
                Text(book.state, style = MaterialTheme.typography.bodyMedium)
                RatingBar(rating = book.rating)
                Text(book.price, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun RatingBar(rating: Float) {
    Row {
        repeat(rating.toInt()) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = stringResource(R.string.filled_star),
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        repeat(5 - rating.toInt()) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = stringResource(R.string.empty_star),
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

@Composable
fun BookList(books: List<Book>, onItemClick: (Book) -> Unit) {
    LazyColumn {
        items(books) { book ->
            BookItem(book = book, onItemClick = onItemClick)
            Divider(
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 40.dp),
                thickness = 3.dp,
                color = Color(0xFFc9e5d7)
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun Encabezado(onLogoutClick: () -> Unit, modifier: Modifier = Modifier.background(Color.White)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFc9e5d7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onLogoutClick,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = Color(0xFF78b395),
                    contentDescription = stringResource(R.string.menu)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carrito),
                    contentDescription = stringResource(R.string.carrito),
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color(0xFFc9e5d7))
                )

                Text(
                    text = "Quick Market",
                    color = Color(0xFF008243),
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = onLogoutClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    tint = Color(0xFF78b395),
                    contentDescription = stringResource(R.string.configuracion)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuickMarketScreen() {
    QuickMarketTheme {
        SearchViewScreen(onLogoutClick = {}, onProductClick = {})
    }
}