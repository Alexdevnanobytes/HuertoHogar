package com.example.huertohogar.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huertohogar.data.ProductRepository
import com.example.huertohogar.model.CartItem
import com.example.huertohogar.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class   CartViewModel : ViewModel() {

    // Catálogo (mock)
    private val _catalog = MutableStateFlow(ProductRepository.products)
    val catalog: StateFlow<List<Product>> = _catalog

    // Carrito (lista de items)
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart

    // Total del carrito
    val cartTotal: Int get() = _cart.value.sumOf { it.subtotal }

    fun addProduct(product: Product, qty: Int) {
        if (qty <= 0) return
        val current = _cart.value.toMutableList()
        val ix = current.indexOfFirst { it.product.id == product.id }
        if (ix >= 0) {
            val updated = current[ix].copy(qty = current[ix].qty + qty)
            current[ix] = updated
        } else {
            current.add(CartItem(product, qty))
        }
        _cart.value = current
    }

    fun updateQty(productId: String, newQty: Int) {
        val current = _cart.value.toMutableList()
        val ix = current.indexOfFirst { it.product.id == productId }
        if (ix >= 0) {
            if (newQty <= 0) {
                current.removeAt(ix)
            } else {
                current[ix] = current[ix].copy(qty = newQty)
            }
            _cart.value = current
        }
    }

    fun removeProduct(productId: String) {
        _cart.update { it.filterNot { item -> item.product.id == productId } }
    }

    fun clear() {
        _cart.value = emptyList()
    }
}