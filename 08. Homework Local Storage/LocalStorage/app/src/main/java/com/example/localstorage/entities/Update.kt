package com.example.localstorage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

@Entity
data class Update(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "lastDayUpdated") val lastDayUpdated: String,
    @ColumnInfo(name = "lastTimeUpdated") val lastTimeUpdated: String
)
