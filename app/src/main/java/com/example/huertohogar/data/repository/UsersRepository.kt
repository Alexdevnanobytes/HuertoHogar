package com.example.huertohogar.data.repository

import com.example.huertohogar.data.local.Users
import com.example.huertohogar.data.local.UsersDao
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val dao: UsersDao) {
    val users: Flow<List<Users>> = dao.getAllUsers()

    suspend fun insert(user: Users) = dao.insertUser(user)
    suspend fun update(user: Users) = dao.updateUser(user)
    suspend fun delete(user: Users) = dao.deleteUser(user)
}