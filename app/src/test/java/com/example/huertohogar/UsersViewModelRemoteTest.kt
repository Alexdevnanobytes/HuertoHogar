package com.example.huertohogar

import com.example.huertohogar.data.remote.UsuarioRemote
import com.example.huertohogar.data.repository.UsersRemoteRepository
import com.example.huertohogar.data.repository.UsersRepository
import com.example.huertohogar.viewmodel.UsersViewModel
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class UsersViewModelRemoteTest : StringSpec({

    "registrarUsuarioBackend() debe llamar al repositorio remoto" {
        val mockLocal = mockk<UsersRepository>(relaxed = true)
        val mockRemote = mockk<UsersRemoteRepository>(relaxed = true)

        coEvery { mockRemote.crearUsuario(any()) } returns UsuarioRemote(
            id = 1,
            username = "TestUser",
            password = "123456",
            email = "test@mail.com",
            enabled = true
        )

        val vm = UsersViewModel(mockLocal, mockRemote)

        vm.onNombreChanged("TestUser")
        vm.onCorreoChanged("test@mail.com")
        vm.onClaveChanged("123456")

        runTest {
            vm.registrarUsuarioBackend()

            coVerify {
                mockRemote.crearUsuario(any())
            }
        }
    }
})