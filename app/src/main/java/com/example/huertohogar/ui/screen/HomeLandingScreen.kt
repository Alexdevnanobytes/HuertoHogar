package com.example.huertohogar.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.R
import com.example.huertohogar.ui.components.HomeCard

data class HomeItem(
    val title: String,
    val desc: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLandingScreen(navController: NavController) {
    val items: List<HomeItem> = listOf(
        HomeItem("Usuarios", "Ver, editar y registrar", Icons.Filled.List, "listUsers"),
        HomeItem("Registrar", "Agregar nuevo usuario", Icons.Filled.AccountCircle, "registro"),
        HomeItem("Catálogo", "Explorar nuestros productos ", Icons.Filled.ShoppingCart, "catalogo"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Huerto Hogar", fontWeight = FontWeight.Bold) }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Coloca un drawable temporal si no tienes logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Huerto Hogar",
                    modifier = Modifier.size(96.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Bienvenido 👋",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                "Elige una sección para comenzar",
                style = MaterialTheme.typography.bodyLarge
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
