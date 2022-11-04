package com.example.localstorage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.example.localstorage.models.Flag

@Entity
data class Country(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "capital") var capital: String,
    @ColumnInfo(name = "region") var region: String,
    @ColumnInfo(name = "population") var population: Int,
    @ColumnInfo(name = "area") var area: Float,
//    @ColumnInfo(name = "pngFlag") var pngFlag: String
)
