package com.example.huertohogar.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.model.CartItem
import com.example.huertohogar.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val items by cartViewModel.cart.collectAsState()

    // Paleta de colores coherente
    val primaryColor = Color(0xFF2E7D32) // Verde oscuro
    val secondaryColor = Color(0xFF4CAF50) // Verde
    val backgroundColor = Color(0xFFF5F5F5) // Fondo gris claro
    val cardColor = Color.White // Fondo blanco para contenido
    val textColor = Color(0xFF333333) // Texto oscuro

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Carrito de Compras",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val homeExists = navController.popBackStack("home", inclusive = false)
                        if (!homeExists) {
                            navController.navigate("home") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Inicio",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = backgroundColor
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            // Tarjeta principal para el contenido
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    if (items.isEmpty()) {
                        // Estado vacío
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "🛒",
                                style = MaterialTheme.typography.displayMedium
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Tu carrito está vacío",
                                style = MaterialTheme.typography.headlineSmall,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                "Agrega productos desde el catálogo",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF757575)
                            )
                        }
                    } else {
                        // Lista de productos
                        Text(
                            "Productos en el carrito",
                            style = MaterialTheme.typography.headlineSmall,
                            color = primaryColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(items, key = { it.product.id }) { ci ->
                                CartRow(
                                    item = ci,
                                    onQtyChange = { newQty -> cartViewModel.updateQty(ci.product.id, newQty) },
                                    onRemove = { cartViewModel.removeProduct(ci.product.id) },
                                    primaryColor = primaryColor,
                                    secondaryColor = secondaryColor,
                                    textColor = textColor
                                )
                                Divider(
                                    color = Color(0xFFEEEEEE),
                                    thickness = 1.dp
                                )
                            }
                        }

                        // Resumen y total
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF8F9FA)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                val total = cartViewModel.cartTotal
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Total a pagar:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = textColor
                                    )
                                    Text(
                                        "$$total",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = primaryColor
                                    )
                                }

                                Spacer(Modifier.height(16.dp))

                                Button(
                                    onClick = { /* TODO: flujo de pago */ },
                                    enabled = items.isNotEmpty(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = primaryColor,
                                        contentColor = Color.White
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 4.dp,
                                        pressedElevation = 8.dp
                                    )
                                ) {
                                    Text(
                                        "Continuar con la Compra",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartRow(
    item: CartItem,
    onQtyChange: (Int) -> Unit,
    onRemove: () -> Unit,
    primaryColor: Color,
    secondaryColor: Color,
    textColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Miniatura
            Image(
                painter = painterResource(id = item.product.imageRes),
                contentDescription = item.product.name,
                modifier = Modifier.size(64.dp)
            )

            // Información del producto
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.product.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Text(
                    "Precio: $${item.product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF757575)
                )
                Text(
                    "Subtotal: $${item.subtotal}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
            }

            // Controles de cantidad
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    "Cantidad",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF757575)
                )
                OutlinedTextField(
                    value = item.qty.toString(),
                    onValueChange = {
                        val newText = it.filter { c -> c.isDigit() }.take(3)
                        onQtyChange(newText.toIntOrNull() ?: 0)
                    },
                    singleLine = true,
                    modifier = Modifier.width(80.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = secondaryColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = secondaryColor,
                        cursorColor = secondaryColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )
            }

            // Botón eliminar
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = Color(0xFFD32F2F) // Rojo para acciones destructivas
                )
            }
        }
    }
}