package com.example.huertohogar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.data.local.Users
import com.example.huertohogar.viewmodel.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUsersScreen(
    navController: NavController,
    viewModel: UsersViewModel,
    modifier: Modifier = Modifier
) {
    val users by viewModel.users.collectAsState(initial = emptyList())

    // 🎨 Paleta coherente con RegistroScreen
    val primaryColor = Color(0xFF2E7D32)   // Verde oscuro
    val secondaryColor = Color(0xFF4CAF50) // Verde medio
    val backgroundColor = Color(0xFFF5F5F5) // Fondo gris claro
    val cardColor = Color.White
    val textColor = Color(0xFF333333)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Usuarios Registrados",
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
            modifier = modifier
                .fillMaxSize()
                .padding(inner)
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            // Contenedor visual
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Lista de usuarios registrados",
                        style = MaterialTheme.typography.headlineSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(users) { user ->
                            UserItem(
                                user = user,
                                onEditClick = { navController.navigate("editUser/${user.id}") },
                                onDeleteClick = { viewModel.deleteUser(user) },
                                textColor = textColor,
                                secondaryColor = secondaryColor
                            )
                            Divider(
                                thickness = 1.dp,
                                color = Color(0xFFE0E0E0)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("registro") },
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
                            "Agregar Nuevo Usuario",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: Users,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    textColor: Color,
    secondaryColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = user.nombre,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
            Text(
                text = user.correo,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF757575)
            )
            Text(
                text = user.direccion,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF757575)
            )
            Text(
                text = "Términos: ${if (user.aceptaTerminos) "Aceptados" else "No aceptados"}",
                style = MaterialTheme.typography.bodySmall,
                color = if (user.aceptaTerminos) secondaryColor else Color(0xFFB71C1C)
            )
        }

        Row {
            IconButton(onClick = onEditClick) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Editar",
                    tint = secondaryColor
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = Color(0xFFD32F2F)
                )
            }
        }
    }
}
