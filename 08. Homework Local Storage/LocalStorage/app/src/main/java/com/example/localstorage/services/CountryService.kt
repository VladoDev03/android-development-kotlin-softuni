package com.example.localstorage.services

import com.example.localstorage.models.CountryInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("all")
    fun getCountries(): Call<List<CountryInfo>>

    @GET("name/{name}")
    fun getCountry(@Path("name") name: String): Call<List<CountryInfo>>
}