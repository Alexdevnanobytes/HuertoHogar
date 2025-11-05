package com.example.huertohogar.data

import com.example.huertohogar.R
import com.example.huertohogar.model.Product

object ProductRepository {
    // Usa drawables reales; añade imágenes a res/drawable
    val products: List<Product> = listOf(
        Product(id = "P01", name = "Manzana",      price = 990,  imageRes = R.drawable.manzana),
        Product(id = "P02", name = "Miel KG",     price = 790,  imageRes = R.drawable.miel),
        Product(id = "P03", name = "Zanahoria",   price = 690,  imageRes = R.drawable.zanahoria),
        Product(id = "P04", name = "Pimentón",       price = 1990, imageRes = R.drawable.pimenton_rojo),
        Product(id = "P05", name = "Platano",    price = 1490, imageRes = R.drawable.platano),
        Product(id = "P06", name = "Quinoa",      price = 850,  imageRes = R.drawable.quinoa)
    )
}
