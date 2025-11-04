package com.example.huertohogar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import com.example.huertohogar.viewmodel.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    navController: NavController,
    viewModel: UsersViewModel,
    userId: Int,
    modifier: Modifier = Modifier
) {
    // Buscar el user por ID
    val users by viewModel.users.collectAsState(initial = emptyList())
    val user = users.find { it.id == userId }

    // Estados locales para edición
    var nombre by remember { mutableStateOf(user?.nombre ?: "") }
    var correo by remember { mutableStateOf(user?.correo ?: "") }
    var clave by remember { mutableStateOf(user?.clave ?: "") }
    var direccion by remember { mutableStateOf(user?.direccion ?: "") }
    var aceptaTerminos by remember { mutableStateOf(user?.aceptaTerminos ?: false) }

    // Estados para errores
    var errores by remember { mutableStateOf(mapOf<String, String>()) }

    // Paleta de colores coherente
    val primaryColor = Color(0xFF2E7D32) // Verde oscuro
    val secondaryColor = Color(0xFF4CAF50) // Verde
    val backgroundColor = Color(0xFFF5F5F5) // Fondo gris claro
    val cardColor = Color.White // Fondo blanco para contenido
    val textColor = Color(0xFF333333) // Texto oscuro
    val errorColor = Color(0xFFD32F2F) // Rojo para errores

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Editar Usuario",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
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
                    if (user != null) {
                        IconButton(
                            onClick = {
                                viewModel.deleteUser(user)
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color.White
                            )
                        }
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
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            // Tarjeta principal para el formulario
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
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Título de bienvenida
                    Text(
                        "Editar Información",
                        style = MaterialTheme.typography.headlineSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Modifica los datos del usuario",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF757575)
                    )

                    Spacer(Modifier.height(8.dp))

                    // Campos del formulario
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it; errores = errores - "nombre" },
                        label = { Text("Nombre completo") },
                        isError = errores["nombre"] != null,
                        supportingText = {
                            errores["nombre"]?.let {
                                Text(it, color = errorColor)
                            }
                        },
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

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it; errores = errores - "correo" },
                        label = { Text("Correo electrónico") },
                        isError = errores["correo"] != null,
                        supportingText = {
                            errores["correo"]?.let {
                                Text(it, color = errorColor)
                            }
                        },
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

                    OutlinedTextField(
                        value = clave,
                        onValueChange = { clave = it; errores = errores - "clave" },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = errores["clave"] != null,
                        supportingText = {
                            errores["clave"]?.let {
                                Text(it, color = errorColor)
                            }
                        },
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

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it; errores = errores - "direccion" },
                        label = { Text("Dirección") },
                        isError = errores["direccion"] != null,
                        supportingText = {
                            errores["direccion"]?.let {
                                Text(it, color = errorColor)
                            }
                        },
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

                    // Checkbox de términos
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Checkbox(
                            checked = aceptaTerminos,
                            onCheckedChange = { aceptaTerminos = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = secondaryColor,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Acepta los términos y condiciones",
                            color = textColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    // Botón principal de guardar
                    Button(
                        onClick = {
                            // Validar formulario
                            val nuevosErrores = mutableMapOf<String, String>()

                            if (nombre.isBlank()) nuevosErrores["nombre"] = "El nombre no puede estar vacío"
                            if (!correo.contains("@")) nuevosErrores["correo"] = "Correo inválido"
                            if (clave.length < 6) nuevosErrores["clave"] = "La clave debe tener al menos 6 caracteres"
                            if (direccion.isBlank()) nuevosErrores["direccion"] = "La dirección no puede estar vacía"

                            errores = nuevosErrores

                            if (nuevosErrores.isEmpty() && user != null) {
                                val userActualizado = user.copy(
                                    nombre = nombre,
                                    correo = correo,
                                    clave = clave,
                                    direccion = direccion,
                                    aceptaTerminos = aceptaTerminos
                                )
                                viewModel.updateUser(userActualizado)
                                navController.popBackStack()
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
                            "Guardar Cambios",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Botón secundario para cancelar
                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = secondaryColor
                        ),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text(
                            "Cancelar",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}