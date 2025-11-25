package com.example.huertohogar.data.remote

import com.example.huertohogar.model.Planta
import com.example.huertohogar.model.MiHuerto
import retrofit2.http.*

interface ApiService {

    // ========== ENDPOINTS PARA PLANTAS ==========

    @GET("api/plantas")
    suspend fun getPlantas(): List<Planta>

    @GET("api/plantas/{id}")
    suspend fun getPlantaById(@Path("id") id: Int): Planta

    @POST("api/plantas")
    suspend fun createPlanta(@Body planta: Planta): Planta

    @PUT("api/plantas/{id}")
    suspend fun updatePlanta(@Path("id") id: Int, @Body planta: Planta): Planta

    @DELETE("api/plantas/{id}")
    suspend fun deletePlanta(@Path("id") id: Int)


    // ========== ENDPOINTS PARA MI HUERTO ==========

    @GET("api/mihuerto")
    suspend fun getMiHuerto(): List<MiHuerto>

    @GET("api/mihuerto/{id}")
    suspend fun getHuertoById(@Path("id") id: Int): MiHuerto

    @POST("api/mihuerto")
    suspend fun addPlantaAHuerto(@Body huerto: MiHuerto): MiHuerto

    @PUT("api/mihuerto/{id}")
    suspend fun updateHuerto(@Path("id") id: Int, @Body huerto: MiHuerto): MiHuerto

    @DELETE("api/mihuerto/{id}")
    suspend fun removeFromHuerto(@Path("id") id: Int)
}