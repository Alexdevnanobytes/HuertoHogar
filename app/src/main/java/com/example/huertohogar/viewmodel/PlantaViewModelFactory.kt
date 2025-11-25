package com.example.huertohogar.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogar.data.repository.PlantaRepository
import kotlinx.coroutines.Dispatchers

class PlantaViewModelFactory(
    private val repo: PlantaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlantaViewModel(
            repository = repo,
            dispatcher = Dispatchers.IO
        ) as T
    }
}