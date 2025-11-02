package com.example.huertohogar.ui.screen

import androidx.compose.foundation.Image
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
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
                        Icon(Icons.Filled.Home, contentDescription = "Inicio")
                    }
                }
            )
        }
    ) { inner ->
        Column(Modifier.padding(inner).padding(12.dp)) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f, fill = true)
            ) {
                items(items, key = { it.product.id }) { ci ->
                    CartRow(
                        item = ci,
                        onQtyChange = { newQty -> cartViewModel.updateQty(ci.product.id, newQty) },
                        onRemove = { cartViewModel.removeProduct(ci.product.id) }
                    )
                    Divider()
                }
            }

            // Total
            val total = cartViewModel.cartTotal
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", style = MaterialTheme.typography.titleMedium)
                Text("$ $total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { /* TODO: flujo de pago */ },
                enabled = items.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}

@Composable
private fun CartRow(
    item: CartItem,
    onQtyChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Miniatura
        Image(
            painter = painterResource(id = item.product.imageRes),
            contentDescription = item.product.name,
            modifier = Modifier.size(56.dp)
        )

        // Nombre
        Text(
            text = item.product.name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )

        // Cantidad (editable)
        var text by remember(item.qty) { mutableStateOf(item.qty.toString()) }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it.filter { c -> c.isDigit() }.take(3)
                onQtyChange(text.toIntOrNull() ?: 0)
            },
            singleLine = true,
            modifier = Modifier.width(72.dp)
        )

        // Valor unitario
        Text("$ ${item.product.price}", modifier = Modifier.widthIn(min = 72.dp))

        // Subtotal
        Text("$ ${item.subtotal}", modifier = Modifier.widthIn(min = 90.dp), fontWeight = FontWeight.SemiBold)

        // Borrar
        IconButton(onClick = onRemove) {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
        }
    }
}
