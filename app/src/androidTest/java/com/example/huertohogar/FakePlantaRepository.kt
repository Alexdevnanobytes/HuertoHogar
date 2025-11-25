package com.example.huertohogar
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.huertohogar.model.Planta
import com.example.huertohogar.ui.screen.PlantaScreen
import com.example.huertohogar.viewmodel.PlantaViewModel
import com.example.huertohogar.data.repository.PlantaRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class FakePlantaRepository(private val plantas: List<Planta>) : PlantaRepository() {
    override suspend fun getPlantas(): List<Planta> = plantas
}

class PlantaScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun el_nombre_de_planta_debe_aparecer_en_pantalla() = runTest {
        // Datos simulados
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

        // Dispatcher de test
        val dispatcher = StandardTestDispatcher(testScheduler)

        // Repositorio falso
        val fakeRepo = FakePlantaRepository(fakePlantas)

        // ViewModel real pero controlado
        val viewModel = PlantaViewModel(fakeRepo, dispatcher)

        // Avanza la corrutina del init { fetchPlantas() }
        dispatcher.scheduler.advanceUntilIdle()

        composeRule.setContent {
            PlantaScreen(viewModel = viewModel)
        }

        // Verificar que los nombres aparecen
        composeRule.onNodeWithText("Tomate").assertIsDisplayed()
        composeRule.onNodeWithText("Lechuga").assertIsDisplayed()

        // Verificar que las descripciones aparecen
        composeRule.onNodeWithText("Planta de fruto rojo").assertIsDisplayed()
        composeRule.onNodeWithText("Verdura de hoja verde").assertIsDisplayed()
    }

    @Test
    fun mensaje_vacio_debe_aparecer_cuando_no_hay_plantas() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val fakeRepo = FakePlantaRepository(emptyList())
        val viewModel = PlantaViewModel(fakeRepo, dispatcher)

        dispatcher.scheduler.advanceUntilIdle()

        composeRule.setContent {
            PlantaScreen(viewModel = viewModel)
        }

        composeRule.onNodeWithText("No hay plantas disponibles").assertIsDisplayed()
    }
}