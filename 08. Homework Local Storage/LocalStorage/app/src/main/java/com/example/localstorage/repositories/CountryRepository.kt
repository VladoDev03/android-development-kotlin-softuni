package com.example.localstorage.repositories

import com.example.localstorage.models.Country
import com.example.localstorage.models.CountryInfo
import com.example.localstorage.services.CountryService
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

    fun getCountry(name: String): Call<List<CountryInfo>>? {
        return try {
            countryService.getCountry(name)
        } catch (e: Exception) {
            null
        }
    }
}