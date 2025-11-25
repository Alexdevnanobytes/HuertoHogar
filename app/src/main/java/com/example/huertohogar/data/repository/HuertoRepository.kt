package com.example.huertohogar.data.repository
import com.example.huertohogar.model.MiHuerto
import com.example.huertohogar.data.remote.RetrofitInstance

open class HuertoRepository {
    open suspend fun getMiHuerto(): List<MiHuerto> {
        return RetrofitInstance.api.getMiHuerto()
    }

    open suspend fun getHuertoById(id: Int): MiHuerto {
        return RetrofitInstance.api.getHuertoById(id)
    }

    open suspend fun addPlantaAHuerto(huerto: MiHuerto): MiHuerto {
        return RetrofitInstance.api.addPlantaAHuerto(huerto)
    }

    open suspend fun updateHuerto(id: Int, huerto: MiHuerto): MiHuerto {
        return RetrofitInstance.api.updateHuerto(id, huerto)
    }

    open suspend fun removeFromHuerto(id: Int) {
        RetrofitInstance.api.removeFromHuerto(id)
    }
}