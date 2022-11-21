package com.example.finalexam.model

import com.google.gson.annotations.SerializedName

data class CryptoDetailResponse(
    var id: String,
    var name: String,
    var symbol: String,
    @SerializedName("current_price") var currentPrice: Double,
    @SerializedName("market_cap") var marketCap: String,
    var image: String
)