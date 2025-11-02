package com.example.huertohogar.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.ui.components.ProductCard
import com.example.huertohogar.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val products by cartViewModel.catalog.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    // ✅ Ir al Home (fix completo)
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

                    // 🛒 Ir al Carrito
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        },

                floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("carrito") }) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Ir al carrito")
            }
        }
    ) { inner ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(1), // ✅ un producto por fila
            modifier = Modifier
                .padding(inner)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { p ->
                ProductCard(
                    product = p,
                    onAdd = { qty -> cartViewModel.addProduct(p, qty) },
                    modifier = Modifier
                )
            }
        }
    }
}
