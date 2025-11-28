package com.example.huertohogar.data.repository

import com.example.huertohogar.data.remote.RetrofitInstance
import com.example.huertohogar.data.remote.UsuarioRemote

class UsersRemoteRepository {

    suspend fun getUsuarios(): List<UsuarioRemote> {
        return RetrofitInstance.api.getUsuarios()
    }

    suspend fun crearUsuario(usuario: UsuarioRemote): UsuarioRemote {
        return RetrofitInstance.api.crearUsuario(usuario)
    }
}