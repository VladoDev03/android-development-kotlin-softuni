package com.example.workwithapi.models

data class CountryInfo(
    var name: String,
    var capital: String,
    var region: String,
    var population: Int,
    var area: Float,
    var flags: Flag
)