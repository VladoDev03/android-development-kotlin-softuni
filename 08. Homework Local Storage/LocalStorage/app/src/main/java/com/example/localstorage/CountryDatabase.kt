package com.example.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localstorage.daos.CountryDao
import com.example.localstorage.daos.UpdateDao
import com.example.localstorage.entities.Country
import com.example.localstorage.entities.Update

@Database(entities = [Country::class, Update::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun updateDao(): UpdateDao
}