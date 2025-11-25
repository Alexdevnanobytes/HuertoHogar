package com.example.huertohogar.model

data class Planta(
    val id: Int,
    val nombre: String,
    val nombreCientifico: String?,
    val descripcion: String,
    val tiempoGerminacion: Int, // días
    val tiempoCosecha: Int, // días
    val imagenUrl: String?,
    val cuidados: String,
    val riegoFrecuencia: String // ej: "Diario", "Cada 2 días", etc.
)
