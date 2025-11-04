package com.example.huertohogar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun RegistroScreen(
    navController: NavController,
    viewModel: UsersViewModel
) {
    val estado by viewModel.estado.collectAsState()

    // Paleta de colores coherente con la pantalla principal
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
                        "Registrar usuario",
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
                .fillMaxSize()
                .padding(inner)
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
                        "Crear Nueva Cuenta",
                        style = MaterialTheme.typography.headlineSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Completa tus datos para registrarte",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF757575)
                    )

                    Spacer(Modifier.height(8.dp))

                    // Campos del formulario CORREGIDOS
                    OutlinedTextField(
                        value = estado.nombre,
                        onValueChange = viewModel::onNombreChanged,
                        label = {
                            Text(
                                "Nombre completo"
                            )
                        },
                        isError = estado.errores.nombre != null,
                        supportingText = {
                            estado.errores.nombre?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
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
                            unfocusedTextColor = textColor
                        )
                    )

                    OutlinedTextField(
                        value = estado.correo,
                        onValueChange = viewModel::onCorreoChanged,
                        label = {
                            Text(
                                "Correo electrónico"
                            )
                        },
                        isError = estado.errores.correo != null,
                        supportingText = {
                            estado.errores.correo?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
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
                            unfocusedTextColor = textColor
                        )
                    )

                    OutlinedTextField(
                        value = estado.clave,
                        onValueChange = viewModel::onClaveChanged,
                        label = {
                            Text(
                                "Contraseña"
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = estado.errores.clave != null,
                        supportingText = {
                            estado.errores.clave?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
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
                            unfocusedTextColor = textColor
                        )
                    )

                    OutlinedTextField(
                        value = estado.direccion,
                        onValueChange = viewModel::onDireccionChanged,
                        label = {
                            Text(
                                "Dirección"
                            )
                        },
                        isError = estado.errores.direccion != null,
                        supportingText = {
                            estado.errores.direccion?.let{
                                Text(it, color = MaterialTheme.colorScheme.error)
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
                            unfocusedTextColor = textColor
                        )
                    )

                    // Checkbox de términos
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Checkbox(
                            checked = estado.aceptaTerminos,
                            onCheckedChange = viewModel::onAceptarTerminosChanged,
                            colors = CheckboxDefaults.colors(
                                checkedColor = secondaryColor,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Acepto los términos y condiciones",
                            color = textColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    // Botón principal de registro
                    Button(
                        onClick = {
                            if (viewModel.validateForm()) {
                                viewModel.saveUserToDB()
                                navController.navigate("listUsers")
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
                            "Registrar Usuario",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Botón secundario
                    OutlinedButton(
                        onClick = { navController.navigate("listUsers") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = secondaryColor
                        ),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text(
                            "Ver Lista de Usuarios",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}