package com.example.huertohogar.model
import java.time.LocalDate

data class MiHuerto(
    val id: Int,
    val plantaId: Int,
    val plantaNombre: String,
    val fechaSiembra: String, // formato: "2025-11-25"
    val estado: String, // "Germinando", "Creciendo", "Lista para cosechar"
    val notas: String?
)