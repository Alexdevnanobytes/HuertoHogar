package com.example.huertohogar.data.remote

import retrofit2.http.*

interface ApiService {
    @GET("api/v1/usuarios")
    suspend fun getUsuarios(): List<UsuarioRemote>

    @GET("api/v1/usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Long): UsuarioRemote

    @POST("api/v1/usuarios")
    suspend fun crearUsuario(@Body usuario: UsuarioRemote): UsuarioRemote

    @PUT("api/v1/usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id: Long,
        @Body usuario: UsuarioRemote
    ): UsuarioRemote

    @DELETE("api/v1/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long)
}