package com.example.huertohogar.data.remote

data class UsuarioRemote(
    val id: Long? = null,
    val username: String,
    val password: String,
    val email: String?,
    val enabled: Boolean
)