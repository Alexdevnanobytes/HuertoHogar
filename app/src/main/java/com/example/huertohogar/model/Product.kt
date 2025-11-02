package com.example.huertohogar.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val name: String,
    val price: Int, // CLP en enteros; si quieres usa Long
    @DrawableRes val imageRes: Int
)
