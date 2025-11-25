package com.example.huertohogar.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogar.data.repository.HuertoRepository
import kotlinx.coroutines.Dispatchers

class HuertoViewModelFactory(
    private val repo: HuertoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HuertoViewModel(
            repository = repo,
            dispatcher = Dispatchers.IO
        ) as T
    }
}