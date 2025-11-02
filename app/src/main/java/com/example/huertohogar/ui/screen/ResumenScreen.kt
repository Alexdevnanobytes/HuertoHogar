@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.huertohogar.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.huertohogar.viewmodel.UsersViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*


@Composable
fun ResumenScreen(
    navController: NavHostController,
    viewModel: UsersViewModel
){
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen de registro") },
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
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier.padding(inner).padding(16.dp)
        ) {
            Text(text = "Resumen de registro", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Nombre: ${estado.nombre}")
            Text(text = "Correo: ${estado.correo}")
            Text(text = "Direccion: ${estado.direccion}")
            Text(text = "Contraseña: ${estado.clave}")
            Text(text = "Terminos y Condiciones: ${if (estado.aceptaTerminos)"Aceptados" else "No aceptados"}")
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    if (viewModel.validateForm()){
                        navController.navigate("registro")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Volver a Registro") }
        }
    }
}
