package com.example.huertohogar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.ui.components.ProductCard
import com.example.huertohogar.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val products by cartViewModel.catalog.collectAsState()

    // Paleta
    val primaryColor = Color(0xFF2E7D32)   // Verde oscuro
    val secondaryColor = Color(0xFF4CAF50) // Verde
    val backgroundColor = Color(0xFFF5F5F5)

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Catálogo de Productos",
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
                    // Ir a Home (con fix)
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
                        Icon(Icons.Filled.Home, contentDescription = "Inicio", tint = Color.White)
                    }
                    // Ir al Carrito
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = backgroundColor,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = primaryColor,   // popup con tu verde oscuro
                    contentColor = Color.White,
                    actionColor = Color.White,
                    dismissActionContentColor = Color.White
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("carrito") },
                containerColor = secondaryColor,
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Ir al carrito", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver Carrito")
            }
        }
    ) { inner ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(1), // un producto por fila
            modifier = Modifier
                .padding(inner)
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { p ->
                ProductCard(
                    product = p,
                    onAdd = { qty ->
                        if (qty > 0) {
                            cartViewModel.addProduct(p, qty)
                            scope.launch {
                                val res = snackbarHostState.showSnackbar(
                                    message = "Agregado al carrito",
                                    actionLabel = "Ver",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                                if (res == SnackbarResult.ActionPerformed) {
                                    navController.navigate("carrito")
                                }
                            }
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Selecciona una cantidad mayor a 0",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
                    modifier = Modifier
                )
            }
        }
    }
}
