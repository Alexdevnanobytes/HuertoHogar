package com.example.huertohogar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.data.local.Users
import com.example.huertohogar.data.repository.UsersRepository
import com.example.huertohogar.model.UsersUiState
import com.example.huertohogar.model.UsersErrores
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _estado = MutableStateFlow(UsersUiState())
    val estado: StateFlow<UsersUiState> = _estado

    // Flow para obtener todos los users
    val users = repository.users

    fun onNombreChanged(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChanged(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChanged(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccionChanged(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChanged(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun saveUserToDB() {
        viewModelScope.launch {
            val estadoActual = _estado.value
            val user = Users(
                nombre = estadoActual.nombre,
                correo = estadoActual.correo,
                clave = estadoActual.clave,
                direccion = estadoActual.direccion,
                aceptaTerminos = estadoActual.aceptaTerminos
            )
            repository.insert(user)

            // Limpiar el formulario después de guardar
            _estado.update {
                it.copy(
                    nombre = "",
                    correo = "",
                    clave = "",
                    direccion = "",
                    aceptaTerminos = false,
                    errores = UsersErrores()
                )
            }
        }
    }

    fun updateUser(user: Users) {
        viewModelScope.launch {
            repository.update(user)
        }
    }

    fun deleteUser(user: Users) {
        viewModelScope.launch {
            repository.delete(user)
        }
    }

    fun validateForm(): Boolean {
        val estadoActual = _estado.value
        val errores = UsersErrores(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre no puede estar vacío" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6) "La clave debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "La dirección no puede estar vacía" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }
}