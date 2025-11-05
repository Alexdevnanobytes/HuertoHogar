package com.example.huertohogar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    val users by viewModel.users.collectAsState(initial = emptyList())
    val user = users.find { it.id == userId }

    if (user == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Editar Usuario", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                )
            }
        ) { inner ->
            Box(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Usuario no encontrado")
            }
        }
        return
    }

    // Estados
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(user.aceptaTerminos) }
    var errores by remember { mutableStateOf(mapOf<String, String>()) }

    // Colores
    val primaryColor = Color(0xFF2E7D32)
    val secondaryColor = Color(0xFF4CAF50)
    val backgroundColor = Color(0xFFF5F5F5)
    val cardColor = Color.White
    val textColor = Color(0xFF333333)
    val errorColor = Color(0xFFD32F2F)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Usuario", color = Color.White, fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
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
                    IconButton(
                        onClick = {
                            viewModel.deleteUser(user)
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
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
        // ✅ Scroll vertical
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(backgroundColor)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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

                    // Campos con placeholders
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it; errores = errores - "nombre" },
                        label = { Text("Nombre completo") },
                        placeholder = { Text(user.nombre, color = Color(0xFF9E9E9E)) },
                        isError = errores["nombre"] != null,
                        supportingText = { errores["nombre"]?.let { Text(it, color = errorColor) } },
                        modifier = Modifier.fillMaxWidth(),
                        colors = fieldColors(secondaryColor, textColor, errorColor)
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it; errores = errores - "correo" },
                        label = { Text("Correo electrónico") },
                        placeholder = { Text(user.correo, color = Color(0xFF9E9E9E)) },
                        isError = errores["correo"] != null,
                        supportingText = { errores["correo"]?.let { Text(it, color = errorColor) } },
                        modifier = Modifier.fillMaxWidth(),
                        colors = fieldColors(secondaryColor, textColor, errorColor)
                    )

                    OutlinedTextField(
                        value = clave,
                        onValueChange = { clave = it; errores = errores - "clave" },
                        label = { Text("Contraseña (dejar en blanco para mantener)") },
                        placeholder = { Text("••••••••", color = Color(0xFF9E9E9E)) },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = errores["clave"] != null,
                        supportingText = { errores["clave"]?.let { Text(it, color = errorColor) } },
                        modifier = Modifier.fillMaxWidth(),
                        colors = fieldColors(secondaryColor, textColor, errorColor)
                    )

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it; errores = errores - "direccion" },
                        label = { Text("Dirección") },
                        placeholder = { Text(user.direccion, color = Color(0xFF9E9E9E)) },
                        isError = errores["direccion"] != null,
                        supportingText = { errores["direccion"]?.let { Text(it, color = errorColor) } },
                        modifier = Modifier.fillMaxWidth(),
                        colors = fieldColors(secondaryColor, textColor, errorColor)
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

                    Spacer(Modifier.height(12.dp))

                    // Botón Guardar
                    Button(
                        onClick = {
                            val mergedNombre = if (nombre.isBlank()) user.nombre else nombre
                            val mergedCorreo = if (correo.isBlank()) user.correo else correo
                            val mergedClave = if (clave.isBlank()) user.clave else clave
                            val mergedDir = if (direccion.isBlank()) user.direccion else direccion

                            val nuevosErrores = mutableMapOf<String, String>()
                            if (mergedNombre.isBlank()) nuevosErrores["nombre"] = "El nombre no puede estar vacío"
                            if (!mergedCorreo.contains("@")) nuevosErrores["correo"] = "Correo inválido"
                            if (mergedClave.length < 6) nuevosErrores["clave"] = "La clave debe tener al menos 6 caracteres"
                            if (mergedDir.isBlank()) nuevosErrores["direccion"] = "La dirección no puede estar vacía"
                            errores = nuevosErrores

                            if (nuevosErrores.isEmpty()) {
                                val userActualizado = user.copy(
                                    nombre = mergedNombre,
                                    correo = mergedCorreo,
                                    clave = mergedClave,
                                    direccion = mergedDir,
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
                        Text("Guardar Cambios", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                    }

                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = secondaryColor),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text("Cancelar", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
private fun fieldColors(
    secondaryColor: Color,
    textColor: Color,
    errorColor: Color
) = OutlinedTextFieldDefaults.colors(
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
