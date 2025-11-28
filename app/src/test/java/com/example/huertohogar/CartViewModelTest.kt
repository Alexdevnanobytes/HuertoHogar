package com.example.huertohogar

import com.example.huertohogar.model.Product
import com.example.huertohogar.viewmodel.CartViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    private val mockProduct = Product(
        id = "001",
        name = "Tomate Cherry",
        price = 1000,
        imageRes = 0   // valor dummy válido para tests
    )

    private val mockProduct2 = Product(
        id = "002",
        name = "Lechuga",
        price = 1500,
        imageRes = 0   // también dummy
    )

    @Before
    fun setup() {
        viewModel = CartViewModel()
    }

    @Test
    fun `agregar producto nuevo al carrito`() = runTest {
        viewModel.addProduct(mockProduct, qty = 2)

        val cart = viewModel.cart.value
        assertEquals(1, cart.size)
        assertEquals(2, cart[0].qty)
        assertEquals(mockProduct.id, cart[0].product.id)
    }

    @Test
    fun `incrementar cantidad cuando el producto ya existe`() = runTest {
        viewModel.addProduct(mockProduct, 1)
        viewModel.addProduct(mockProduct, 3)

        val cart = viewModel.cart.value
        assertEquals(1, cart.size)
        assertEquals(4, cart[0].qty)
    }

    @Test
    fun `no agregar productos con cantidad cero o negativa`() = runTest {
        viewModel.addProduct(mockProduct, 0)
        viewModel.addProduct(mockProduct, -3)

        assertTrue(viewModel.cart.value.isEmpty())
    }

    @Test
    fun `actualizar cantidad a un valor mayor`() = runTest {
        viewModel.addProduct(mockProduct, 2)

        viewModel.updateQty(productId = "001", newQty = 5)

        val item = viewModel.cart.value.first()
        assertEquals(5, item.qty)
    }

    @Test
    fun `actualizar cantidad a cero elimina el producto del carrito`() = runTest {
        viewModel.addProduct(mockProduct, 2)

        viewModel.updateQty(productId = "001", newQty = 0)

        assertTrue(viewModel.cart.value.isEmpty())
    }

    @Test
    fun `remover producto por ID`() = runTest {
        viewModel.addProduct(mockProduct, 2)
        viewModel.addProduct(mockProduct2, 1)

        viewModel.removeProduct("001")

        val cart = viewModel.cart.value
        assertEquals(1, cart.size)
        assertEquals("002", cart[0].product.id)
    }

    @Test
    fun `limpiar el carrito`() = runTest {
        viewModel.addProduct(mockProduct, 2)
        viewModel.addProduct(mockProduct2, 3)

        viewModel.clear()

        assertTrue(viewModel.cart.value.isEmpty())
    }

    @Test
    fun `calcular el total del carrito`() = runTest {
        viewModel.addProduct(mockProduct, 2)   // 2 × 1000 = 2000
        viewModel.addProduct(mockProduct2, 1) // 1 × 1500 = 1500

        val total = viewModel.cartTotal

        assertEquals(3500, total)
    }
}