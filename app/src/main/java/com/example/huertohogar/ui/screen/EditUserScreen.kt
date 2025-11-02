package com.example.huertohogar.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar User") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
                    if (user != null) {
                        IconButton(
                            onClick = {
                                viewModel.deleteUser(user)
                                navController.popBackStack()
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }

            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; errores = errores - "nombre" },
                label = { Text("Nombre") },
                isError = errores["nombre"] != null,
                supportingText = {
                    errores["nombre"]?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it; errores = errores - "correo" },
                label = { Text("Correo electrónico") },
                isError = errores["correo"] != null,
                supportingText = {
                    errores["correo"]?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it; errores = errores - "clave" },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = errores["clave"] != null,
                supportingText = {
                    errores["clave"]?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it; errores = errores - "direccion" },
                label = { Text("Dirección") },
                isError = errores["direccion"] != null,
                supportingText = {
                    errores["direccion"]?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it }
                )
                Spacer(Modifier.width(8.dp))
                Text("Acepto los términos y condiciones")
            }

            Spacer(Modifier.height(16.dp))

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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}