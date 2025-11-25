package com.example.huertohogar
import com.example.huertohogar.model.Planta
import com.example.huertohogar.data.remote.ApiService
import com.example.huertohogar.viewmodel.PlantaViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class PlantaViewModelTest : StringSpec({

    "plantaList se actualiza correctamente tras fetchPlantas()" {
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

        // Mock API
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getPlantas() } returns fakePlantas

        // Repositorio de test
        val testRepo = TestTablePlantaRepository(mockApi)

        // Dispatcher de test
        val dispatcher = StandardTestDispatcher()

        // ViewModel testeable
        val viewModel = PlantaViewModel(testRepo, dispatcher)

        runTest(dispatcher) {
            viewModel.fetchPlantas()
            advanceUntilIdle()

            viewModel.plantaList.value shouldContainExactly fakePlantas
            viewModel.isLoading.value shouldBe false
            viewModel.error.value shouldBe null
        }
    }

    "error debe actualizarse cuando falla fetchPlantas()" {
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getPlantas() } throws Exception("Error de red")

        val testRepo = TestTablePlantaRepository(mockApi)
        val dispatcher = StandardTestDispatcher()
        val viewModel = PlantaViewModel(testRepo, dispatcher)

        runTest(dispatcher) {
            viewModel.fetchPlantas()
            advanceUntilIdle()

            viewModel.error.value shouldBe "Error al cargar plantas: Error de red"
            viewModel.isLoading.value shouldBe false
        }
    }
})