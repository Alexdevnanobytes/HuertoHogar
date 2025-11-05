package com.example.huertohogar.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.R
import com.example.huertohogar.ui.components.HomeCard
import androidx.compose.material.icons.filled.Place


data class HomeItem(
    val title: String,
    val desc: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String,
    val cardColor: Color // Nuevo parámetro para el color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLandingScreen(navController: NavController) {
    // Definir una paleta de colores para las tarjetas
    val cardColors = listOf(
        Color(0xFF4CAF50), // Verde
        Color(0xFF2196F3), // Azul
        Color(0xFF9C27B0), // Púrpura
        Color(0xFFFF9800), // Naranja
        Color(0xFFF44336), // Rojo
        Color(0xFF607D8B)  // Gris azulado
    )

    val items: List<HomeItem> = listOf(
        HomeItem("Usuarios", "Ver, editar y registrar", Icons.Filled.List, "listUsers", cardColors[0]),
        HomeItem("Registrar", "Agregar nuevo usuario", Icons.Filled.AccountCircle, "registro", cardColors[1]),
        HomeItem("Catálogo", "Explorar nuestros productos", Icons.Filled.ShoppingCart, "catalogo", cardColors[2]),
        HomeItem("Ubicación", "Si quieres comprar presencialmente", Icons.Filled.Place, "ubicacion", cardColors[3]) // 👈 NUEVO

    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Huerto Hogar",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E7D32), // Verde oscuro
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF5F5F5) // Color de fondo de la pantalla
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Fondo gris claro
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Huerto Hogar",
                    modifier = Modifier.size(96.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Bienvenido 👋",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF2E7D32) // Verde oscuro para el texto
            )
            Text(
                "Elige una sección para comenzar",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF757575)
            )
            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 220.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    HomeCard(
                        title = item.title,
                        description = item.desc,
                        icon = item.icon,
                        onClick = { navController.navigate(item.route) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}