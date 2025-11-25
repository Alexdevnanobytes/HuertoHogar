package com.example.huertohogar.data.repository

import com.example.huertohogar.model.Planta
import com.example.huertohogar.data.remote.RetrofitInstance

open class PlantaRepository {
    open suspend fun getPlantas(): List<Planta> {
        return RetrofitInstance.api.getPlantas()
    }

    open suspend fun getPlantaById(id: Int): Planta {
        return RetrofitInstance.api.getPlantaById(id)
    }

    open suspend fun createPlanta(planta: Planta): Planta {
        return RetrofitInstance.api.createPlanta(planta)
    }

    open suspend fun updatePlanta(id: Int, planta: Planta): Planta {
        return RetrofitInstance.api.updatePlanta(id, planta)
    }

    open suspend fun deletePlanta(id: Int) {
        RetrofitInstance.api.deletePlanta(id)
    }
}