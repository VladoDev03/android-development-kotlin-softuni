package com.example.finalexam.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalexam.db.entity.CryptoDetails

@Dao
interface CryptoDao {

    @Query("SELECT * FROM cryptos")
    suspend fun getCryptos(): List<CryptoDetails>

    @Query("SELECT * FROM cryptos WHERE uid=:id")
    suspend fun getCryptoById(id: String): CryptoDetails

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(cryptos: List<CryptoDetails>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(crypto: CryptoDetails)
}