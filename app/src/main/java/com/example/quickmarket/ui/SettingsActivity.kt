package com.example.quickmarket

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(onLogoutClick: () -> Unit) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFc9e5d7))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFc9e5d7)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onLogoutClick,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = Color(0xFF78b395),
                            contentDescription = "menu"
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
                            contentDescription = "carrito",
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
                            contentDescription = "configuracion"
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xFFc9e5d7)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Mi Cuenta",
                        color = Color(0xFF008243),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    )
                }
            }
        },
        content = {
            cuenta()
        }
    )
}

@Composable
fun cuenta() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "perfil",
                modifier = Modifier
            )
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "icono",
                tint = Color.White,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF83c88d))
                    .offset()
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 350.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.perfil2),
                contentDescription = "perfil2",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "Perfil y contraseña",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
        Divider(
            color = Color(0xFFc9e5d7),
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pedido),
                contentDescription = "pedido",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "Pedidos",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
        Divider(
            color = Color(0xFFc9e5d7),
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.valoraciones),
                contentDescription = "valoraciones",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "Mis valoraciones",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
        Divider(
            color = Color(0xFFc9e5d7),
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 40.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.envio),
                contentDescription = "envio",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "Direcciones de envío",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
        Divider(
            color = Color(0xFFc9e5d7),
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 40.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.tarjetas),
                contentDescription = "tarjetas",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "Tarjetas guardadas",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
        Divider(
            color = Color(0xFFc9e5d7),
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.notificacion),
                contentDescription = "notificacion",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "Notificaciones",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
        Divider(
            color = Color(0xFFc9e5d7),
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 40.dp)
        )
    }
}