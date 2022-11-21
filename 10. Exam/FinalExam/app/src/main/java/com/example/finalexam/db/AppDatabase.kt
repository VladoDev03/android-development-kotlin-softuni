package com.example.finalexam.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalexam.db.dao.CryptoDao
import com.example.finalexam.db.entity.CryptoDetails

@Database(entities = [CryptoDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao
}