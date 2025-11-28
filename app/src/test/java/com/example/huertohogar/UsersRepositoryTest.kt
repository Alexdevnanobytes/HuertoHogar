package com.example.huertohogar

import com.example.huertohogar.data.local.Users
import com.example.huertohogar.data.local.UsersDao
import com.example.huertohogar.data.repository.UsersRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class UsersRepositoryTest : StringSpec({

    "insert debe llamar a dao.insertUser()" {
        val mockDao = mockk<UsersDao>(relaxed = true)
        val repo = UsersRepository(mockDao)

        val user = Users(
            nombre = "Juan",
            correo = "mail@mail.com",
            clave = "123456",
            direccion = "Calle",
            aceptaTerminos = true
        )

        coEvery { mockDao.insertUser(any()) } returns Unit

        repo.insert(user)

        coVerify { mockDao.insertUser(user) }
    }
})