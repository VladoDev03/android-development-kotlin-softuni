package com.example.workwithapi.services

import com.example.workwithapi.models.Country
import retrofit2.Call
import retrofit2.http.GET

interface CountryService {
    @GET("all")
    fun getCountries(): Call<List<Country>>
}