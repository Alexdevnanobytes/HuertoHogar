package com.example.huertohogar.ui.screen

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalInspectionMode


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun UbicacionScreen(
    navController: NavController
) {
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
                        color = Color.White
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta descriptiva
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Si quieres comprar presencialmente",
                        style = MaterialTheme.typography.titleMedium,
                        color = primaryColor
                    )
                    Text(
                        "Encuéntranos en nuestra ubicación. Puedes tocar y arrastrar el mapa, hacer zoom y navegar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF757575)
                    )
                }
            }

            // Mapa (iframe) dentro de un WebView / Placeholder en Preview
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val inPreview = LocalInspectionMode.current

                if (inPreview) {
                    // 🔹 Placeholder para el Preview de Android Studio
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Vista previa del mapa\ndisponible solo en emulador/dispositivo",
                            color = Color(0xFF616161),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            WebView(context).apply {
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.cacheMode = WebSettings.LOAD_DEFAULT
                                settings.loadWithOverviewMode = true
                                settings.useWideViewPort = true
                                // Si tu iframe fuera http (no recomendado), permitir mixto:
                                // settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                                webViewClient = WebViewClient()

                                val iframe = """
                        <html>
                        <head>
                          <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                          <style>html,body{margin:0;padding:0;height:100%} .map{width:100%;height:100%;border:0}</style>
                        </head>
                        <body>
                          <iframe
                            class="map"
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3329.923086010315!2d-70.64826902431908!3d-33.42409917339776!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x9662cf62a4b1f3c5%3A0x6c9d3a7965a7d95a!2sUniversidad%20de%20Chile!5e0!3m2!1ses-419!2sCL!4v1700000000000!5m2!1ses-419!2sCL"
                            loading="lazy"
                            referrerpolicy="no-referrer-when-downgrade"
                            allowfullscreen>
                          </iframe>
                        </body>
                        </html>
                    """.trimIndent()

                                loadDataWithBaseURL(
                                    "https://maps.google.com",
                                    iframe,
                                    "text/html",
                                    "UTF-8",
                                    null
                                )
                            }
                        }
                    )
                }
            }

        }
    }
}
