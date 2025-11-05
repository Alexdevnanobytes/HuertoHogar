package com.example.huertohogar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar.data.local.Users
import com.example.huertohogar.viewmodel.CartViewModel
import com.example.huertohogar.viewmodel.UsersViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    usersViewModel: UsersViewModel,
    cartViewModel: CartViewModel
) {
    val users by usersViewModel.users.collectAsState(initial = emptyList())

    // 🎨 Paleta consistente con el resto
    val primaryColor = Color(0xFF2E7D32)   // Verde oscuro
    val secondaryColor = Color(0xFF4CAF50) // Verde medio
    val backgroundColor = Color(0xFFF5F5F5)
    val cardColor = Color.White
    val textColor = Color(0xFF333333)
    val errorColor = Color(0xFFD32F2F)

    // Estado UI
    var selectedUserId by remember { mutableStateOf<Int?>(null) }
    var password by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Seleccionar cuenta de pago",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
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
                        Icon(Icons.Filled.Home, contentDescription = "Inicio", tint = Color.White)
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
                    containerColor = primaryColor,
                    contentColor = Color.White,
                    actionColor = Color.White,
                    dismissActionContentColor = Color.White
                )
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card principal
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
                        "Elige una cuenta registrada",
                        style = MaterialTheme.typography.headlineSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )

                    if (users.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay usuarios registrados",
                                style = MaterialTheme.typography.bodyLarge,
                                color = textColor
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(users, key = { it.id }) { u ->
                                UserRowSelectable(
                                    user = u,
                                    selected = selectedUserId == u.id,
                                    onSelect = { selectedUserId = u.id },
                                    textColor = textColor,
                                    secondaryColor = secondaryColor
                                )
                                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                            }
                        }

                        // Contraseña para confirmar compra
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Contraseña de la cuenta elegida") },
                            placeholder = { Text("••••••••", color = Color(0xFF9E9E9E)) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = secondaryColor,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = secondaryColor,
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = secondaryColor,
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor,
                                errorBorderColor = errorColor,
                                errorLabelColor = errorColor,
                                errorSupportingTextColor = errorColor
                            )
                        )

                        Button(
                            onClick = {
                                val sel = selectedUserId?.let { id -> users.firstOrNull { it.id == id } }
                                when {
                                    sel == null -> {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Selecciona una cuenta",
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                    password.isBlank() -> {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Ingresa la contraseña",
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                    // Validación simple: compara con la clave almacenada
                                    sel.clave != password -> {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Contraseña incorrecta",
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                    else -> {
                                        showSuccessDialog = true
                                    }
                                }
                            },
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
                                "Confirmar compra",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }

    // Diálogo de éxito
    if (showSuccessDialog) {
        val userName = users.firstOrNull { it.id == selectedUserId }?.nombre ?: ""
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    // (Opcional) Limpia el carrito
                    cartViewModel.clear()
                    // Vuelve al home
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Text("OK", color = primaryColor)
                }
            },
            title = { Text("Compra realizada") },
            text = { Text("Compra realizada con los datos del usuario: $userName") }
        )
    }
}

@Composable
private fun UserRowSelectable(
    user: Users,
    selected: Boolean,
    onSelect: () -> Unit,
    textColor: Color,
    secondaryColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = secondaryColor,
                unselectedColor = Color.Gray
            )
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(user.nombre, style = MaterialTheme.typography.bodyLarge, color = textColor)
            Text(user.correo, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF757575))
        }
    }
}
