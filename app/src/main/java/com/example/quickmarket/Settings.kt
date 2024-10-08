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
import androidx.compose.ui.res.stringResource
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
                            text = stringResource(R.string.quick_market),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xFFc9e5d7)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(R.string.mi_cuenta),
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
                contentDescription = stringResource(R.string.perfil),
                modifier = Modifier
            )
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.icono),
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
                contentDescription = stringResource(R.string.perfil2),
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(R.string.perfil_y_contrase_a),
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
                contentDescription = stringResource(R.string.pedido),
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(R.string.pedidos),
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
                contentDescription = stringResource(R.string.valoraciones),
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(R.string.mis_valoraciones),
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
                contentDescription = stringResource(R.string.envio),
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(R.string.direcciones_de_envio),
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
                contentDescription = stringResource(R.string.tarjetas),
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(R.string.tarjetas_guardadas),
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
                contentDescription = stringResource(R.string.notificacion),
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(R.string.notificaciones),
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