package com.example.huertohogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.huertohogar.data.local.AppDatabase
import com.example.huertohogar.data.repository.UsersRepository
import com.example.huertohogar.ui.navigation.AppNavigation
import com.example.huertohogar.ui.theme.HuertoHogarTheme
import com.example.huertohogar.viewmodel.CartViewModel
import com.example.huertohogar.viewmodel.UsersViewModel
import com.example.huertohogar.viewmodel.UsersViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Construir DB y Repository
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "huerto_hogar_database"
        )
            .fallbackToDestructiveMigration(false)
            .allowMainThreadQueries() // solo dev
            .build()

        val repository = UsersRepository(db.usersDao())
        val factory = UsersViewModelFactory(repository)   // <-- define factory ANTES de usarlo

        // 2) Instanciar ViewModels (fuera de Compose)
        val vmUsers = ViewModelProvider(this, factory)[UsersViewModel::class.java]
        val vmCart  = ViewModelProvider(this)[CartViewModel::class.java]

        // 3) UI
        setContent {
            HuertoHogarTheme {
                AppNavigation(
                    viewModel = vmUsers,
                    cartViewModel = vmCart,              // <-- pásalo aquí
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
