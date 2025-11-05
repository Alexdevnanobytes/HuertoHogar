package com.example.huertohogar.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun UbicacionScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // 🎨 Paleta consistente
    val primaryColor = Color(0xFF2E7D32)   // Verde oscuro
    val secondaryColor = Color(0xFF4CAF50) // Verde medio
    val backgroundColor = Color(0xFFF5F5F5)
    val cardColor = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ubicación",
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
        containerColor = backgroundColor
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            // Tarjeta descriptiva
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "🥬 Visita La Huerta",
                        style = MaterialTheme.typography.titleMedium,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Encuentra nuestra tienda. Puedes tocar y arrastrar el mapa, hacer zoom y ver cómo llegar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF757575)
                    )
                }
            }

            Button(
                onClick = {
                    // Usar el Place ID directo de Google Maps
                    val placeId = "ChIJ416oveBVYpYR8hkN_wY2_ak"
                    val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=La+Huerta&query_place_id=$placeId")
                    val intent = Intent(Intent.ACTION_VIEW, uri)

                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // Fallback: usar coordenadas
                        val fallbackUri = Uri.parse("geo:-33.6693073,-71.2152604?q=La+Huerta")
                        val fallbackIntent = Intent(Intent.ACTION_VIEW, fallbackUri)
                        context.startActivity(fallbackIntent)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
            ) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Abrir en Google Maps / Ver Ruta")
            }

            // Información adicional
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "🥬 Información de Contacto - La Huerta",
                        style = MaterialTheme.typography.titleSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "• Ubicación: Melipilla, Región Metropolitana\n" +
                                "• Coordenadas: -33.6693073, -71.2152604\n" +
                                "• Horario: Consultar en Google Maps\n" +
                                "• Productos frescos y de calidad",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF757575)
                    )
                }
            }
        }
    }
}