package com.example.huertohogar.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.model.Planta
import com.example.huertohogar.data.repository.PlantaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class PlantaViewModel(
    private val repository: PlantaRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _plantaList = MutableStateFlow<List<Planta>>(emptyList())
    open val plantaList: StateFlow<List<Planta>> = _plantaList

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    open val error: StateFlow<String?> = _error

    init {
        fetchPlantas()
    }

    open fun fetchPlantas() {
        viewModelScope.launch(dispatcher) {
            try {
                _isLoading.value = true
                _error.value = null
                _plantaList.value = repository.getPlantas()
            } catch (e: Exception) {
                _error.value = "Error al cargar plantas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPlanta(planta: Planta) {
        viewModelScope.launch(dispatcher) {
            try {
                _isLoading.value = true
                repository.createPlanta(planta)
                fetchPlantas() // Recargar lista
            } catch (e: Exception) {
                _error.value = "Error al agregar planta: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePlanta(id: Int) {
        viewModelScope.launch(dispatcher) {
            try {
                repository.deletePlanta(id)
                fetchPlantas()
            } catch (e: Exception) {
                _error.value = "Error al eliminar planta: ${e.message}"
            }
        }
    }
}