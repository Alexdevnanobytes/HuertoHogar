package com.example.huertohogar.model

data class CartItem(
    val product: Product,
    val qty: Int
) {
    val subtotal: Int get() = product.price * qty
}
