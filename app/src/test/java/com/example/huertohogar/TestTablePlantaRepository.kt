package com.example.huertohogar
import com.example.huertohogar.model.Planta
import com.example.huertohogar.data.remote.ApiService
import com.example.huertohogar.data.repository.PlantaRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class TestTablePlantaRepository(private val testApi: ApiService) : PlantaRepository() {
    override suspend fun getPlantas(): List<Planta> {
        return testApi.getPlantas()
    }

    override suspend fun getPlantaById(id: Int): Planta {
        return testApi.getPlantaById(id)
    }
}

class PlantaRepositoryTest : StringSpec({

    "getPlantas() debe retornar una lista de plantas simulada" {
        val fakePlantas = listOf(
            Planta(
                id = 1,
                nombre = "Tomate",
                nombreCientifico = "Solanum lycopersicum",
                descripcion = "Planta de fruto rojo",
                tiempoGerminacion = 7,
                tiempoCosecha = 80,
                imagenUrl = null,
                cuidados = "Requiere sol directo",
                riegoFrecuencia = "Diario"
            ),
            Planta(
                id = 2,
                nombre = "Lechuga",
                nombreCientifico = "Lactuca sativa",
                descripcion = "Verdura de hoja verde",
                tiempoGerminacion = 5,
                tiempoCosecha = 45,
                imagenUrl = null,
                cuidados = "Mantener húmeda",
                riegoFrecuencia = "Cada 2 días"
            )
        )

        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getPlantas() } returns fakePlantas

        val repo = TestTablePlantaRepository(mockApi)

        runTest {
            val result = repo.getPlantas()
            result shouldContainExactly fakePlantas
        }
    }

    "getPlantaById() debe retornar una planta específica" {
        val fakePlanta = Planta(
            id = 1,
            nombre = "Tomate",
            nombreCientifico = "Solanum lycopersicum",
            descripcion = "Planta de fruto rojo",
            tiempoGerminacion = 7,
            tiempoCosecha = 80,
            imagenUrl = null,
            cuidados = "Requiere sol directo",
            riegoFrecuencia = "Diario"
        )

        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getPlantaById(1) } returns fakePlanta

        val repo = TestTablePlantaRepository(mockApi)

        runTest {
            val result = repo.getPlantaById(1)
            result shouldBe fakePlanta
        }
    }
})
