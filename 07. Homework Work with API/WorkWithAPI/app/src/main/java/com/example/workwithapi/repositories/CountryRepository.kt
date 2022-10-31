package com.example.workwithapi.repositories

import com.example.workwithapi.models.Country
import com.example.workwithapi.services.CountryService
import retrofit2.Call

class CountryRepository constructor(
    private val countryService: CountryService
) {
    fun getCountries(): Call<List<Country>>? {
        return try {
            countryService.getCountries()
        } catch (e: Exception) {
            null
        }
    }

    fun getCountry(): Call<Country>? {
        return try {
            countryService.getCountry()
        } catch (e: Exception) {
            null
        }
    }
}