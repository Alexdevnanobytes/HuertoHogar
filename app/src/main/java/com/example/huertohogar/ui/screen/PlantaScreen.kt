package com.example.huertohogar.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huertohogar.viewmodel.PlantaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantaScreen(viewModel: PlantaViewModel) {
    val plantas = viewModel.plantaList.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val error = viewModel.error.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Catálogo de Plantas") }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchPlantas() }) {
                            Text("Reintentar")
                        }
                    }
                }
                plantas.isEmpty() -> {
                    Text(
                        text = "No hay plantas disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 16.dp)
                    ) {
                        items(plantas) { planta ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(all = 16.dp)) {
                                    Text(
                                        text = planta.nombre,
                                        style = MaterialTheme.typography.titleLarge
                                    )

                                    planta.nombreCientifico?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = planta.descripcion,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(
                                                text = "Germinación:",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                            Text(
                                                text = "${planta.tiempoGerminacion} días",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }

                                        Column {
                                            Text(
                                                text = "Cosecha:",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                            Text(
                                                text = "${planta.tiempoCosecha} días",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }

                                        Column {
                                            Text(
                                                text = "Riego:",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                            Text(
                                                text = planta.riegoFrecuencia,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Cuidados: ${planta.cuidados}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}