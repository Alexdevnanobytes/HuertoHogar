package com.example.huertohogar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.data.local.Users
import com.example.huertohogar.data.remote.UsuarioRemote
import com.example.huertohogar.data.repository.UsersRemoteRepository
import com.example.huertohogar.data.repository.UsersRepository
import com.example.huertohogar.model.UsersUiState
import com.example.huertohogar.model.UsersErrores
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repositoryLocal: UsersRepository,
    private val repositoryRemote: UsersRemoteRepository = UsersRemoteRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(UsersUiState())
    val estado: StateFlow<UsersUiState> = _estado

    // Flow para obtener todos los users
    val users = repositoryLocal.users

    // ------------------------
    // Actualizar campos del formulario
    // ------------------------
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

    // ------------------------
    // Guardar en BD local (Room)
    // ------------------------
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
            repositoryLocal.insert(user)

            // Limpiar formulario
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
            repositoryLocal.update(user)
        }
    }

    fun deleteUser(user: Users) {
        viewModelScope.launch {
            repositoryLocal.delete(user)
        }
    }

    // ------------------------
    // Validación de formulario
    // ------------------------
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

    // ------------------------
    // Enviar usuario al BACKEND (Spring Boot)
    // ------------------------
    fun registrarUsuarioBackend() {
        viewModelScope.launch {

            val estadoActual = estado.value

            val nuevo = UsuarioRemote(
                username = estadoActual.nombre,
                password = estadoActual.clave,
                email = estadoActual.correo,
                enabled = true
            )

            try {
                val creado = repositoryRemote.crearUsuario(nuevo)
                println("Usuario creado en backend: $creado")
            } catch (e: Exception) {
                println("Error al crear usuario backend: ${e.message}")
            }
        }
    }
}
