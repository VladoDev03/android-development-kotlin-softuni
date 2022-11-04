package com.example.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localstorage.daos.CountryDao
import com.example.localstorage.entities.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
}