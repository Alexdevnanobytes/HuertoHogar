package com.example.huertohogar


import com.example.huertohogar.data.local.Users
import com.example.huertohogar.data.repository.UsersRemoteRepository
import com.example.huertohogar.data.repository.UsersRepository
import com.example.huertohogar.viewmodel.UsersViewModel
import io.kotest.core.spec.style.StringSpec
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class UsersViewModelLocalInsertTest : StringSpec({

    "saveUserToDB() debe llamar al repositorio local para insertar" {
        val mockLocal = mockk<UsersRepository>(relaxed = true)
        val mockRemote = mockk<UsersRemoteRepository>(relaxed = true)

        val vm = UsersViewModel(mockLocal, mockRemote)

        vm.onNombreChanged("Juan Perez")
        vm.onCorreoChanged("correo@mail.com")
        vm.onClaveChanged("123456")
        vm.onDireccionChanged("Calle 1")

        runTest {
            vm.saveUserToDB()

            coVerify {
                mockLocal.insert(any<Users>())
            }
        }
    }
})