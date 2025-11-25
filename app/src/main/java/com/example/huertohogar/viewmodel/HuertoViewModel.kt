package com.example.huertohogar.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.model.MiHuerto
import com.example.huertohogar.data.repository.HuertoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class HuertoViewModel(
    private val repository: HuertoRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _huertoList = MutableStateFlow<List<MiHuerto>>(emptyList())
    open val huertoList: StateFlow<List<MiHuerto>> = _huertoList

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    open val error: StateFlow<String?> = _error

    init {
        fetchMiHuerto()
    }

    open fun fetchMiHuerto() {
        viewModelScope.launch(dispatcher) {
            try {
                _isLoading.value = true
                _error.value = null
                _huertoList.value = repository.getMiHuerto()
            } catch (e: Exception) {
                _error.value = "Error al cargar huerto: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPlantaAHuerto(huerto: MiHuerto) {
        viewModelScope.launch(dispatcher) {
            try {
                repository.addPlantaAHuerto(huerto)
                fetchMiHuerto()
            } catch (e: Exception) {
                _error.value = "Error al agregar al huerto: ${e.message}"
            }
        }
    }

    fun removeFromHuerto(id: Int) {
        viewModelScope.launch(dispatcher) {
            try {
                repository.removeFromHuerto(id)
                fetchMiHuerto()
            } catch (e: Exception) {
                _error.value = "Error al eliminar del huerto: ${e.message}"
            }
        }
    }
}