package com.example.localstorage.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.localstorage.entities.Country
import com.example.localstorage.entities.Update

@Dao
interface UpdateDao {
    @Query("SELECT * FROM `update` LIMIT 1")
    suspend fun get(): Update

    @Insert
    suspend fun insert(update: Update)

    @androidx.room.Update
    suspend fun update(update: Update)
}