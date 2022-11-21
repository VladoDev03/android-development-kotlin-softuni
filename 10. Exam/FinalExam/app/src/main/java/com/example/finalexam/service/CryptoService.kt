package com.example.finalexam.service

import com.example.finalexam.model.CryptoDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoService {
    @GET("coins/markets?vs_currency=usd")
    fun getCryptos(): Call<List<CryptoDetailResponse>>

    @GET("coins/{id}")
    fun getCryptoById(@Path("id") id: String): Call<CryptoDetailResponse>
}