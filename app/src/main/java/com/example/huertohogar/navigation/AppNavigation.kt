package com.example.huertohogar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huertohogar.ui.screen.*
import com.example.huertohogar.viewmodel.CartViewModel
import com.example.huertohogar.viewmodel.UsersViewModel

@Composable
fun AppNavigation(
    viewModel: UsersViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home")      { HomeLandingScreen(navController) }
        composable("listUsers") { ListUsersScreen(navController, viewModel) }

        // Catálogo y Carrito
        composable("catalogo")  { CatalogoScreen(navController, cartViewModel) }
        composable("carrito")   { CarritoScreen(navController, cartViewModel) }

        // Ahora estas dos deben aceptar navController (las corregimos abajo)
        composable("huerto")    { HuertoScreen(navController) }
        composable("perfil")    { PerfilScreen(navController) }

        // Rutas internas
        composable("registro")  { RegistroScreen(navController, viewModel) }
        composable("resumen")   { ResumenScreen(navController, viewModel) }
        composable("editUser/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            EditUserScreen(navController, viewModel, userId)
        }
        composable("ubicacion") { UbicacionScreen(navController) }

        composable("pago") {
            PaymentScreen(
                navController = navController,
                usersViewModel = viewModel,      // tu UsersViewModel que ya pasas a AppNavigation
                cartViewModel = cartViewModel    // el CartViewModel que ya usas en carrito
            )
        }

    }
}
