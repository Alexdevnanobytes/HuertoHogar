package com.example.huertohogar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}