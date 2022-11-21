package com.example.finalexam.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptos")
data class CryptoDetails(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "price") var currentPrice: String,
    @ColumnInfo(name = "logo") var logo: String,
    @ColumnInfo(name = "market_cap") var marketCap: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean
)