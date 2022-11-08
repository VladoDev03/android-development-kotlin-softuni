package com.example.localstorage.daos

import androidx.room.*
import com.example.localstorage.entities.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    suspend fun getAll(): List<Country>

    @Query("SELECT * FROM country WHERE name=:countryName")
    suspend fun getByName(countryName: String): List<Country>

    @Insert
    suspend fun insertMany(countries: List<Country>)

    @Query("DELETE FROM country")
    suspend fun deleteAll()

    @Update
    suspend fun update(update: Country)
}