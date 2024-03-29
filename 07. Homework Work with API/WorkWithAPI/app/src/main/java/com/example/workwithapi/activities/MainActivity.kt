package com.example.workwithapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workwithapi.adapters.CountryAdapter
import com.example.workwithapi.databinding.ActivityMainBinding
import com.example.workwithapi.models.Country
import com.example.workwithapi.repositories.CountryRepository
import com.example.workwithapi.services.CountryService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()) // Not required
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountryRepository(countryService)

        countryRepository.getCountries()?.enqueue(object: Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                val countries = response.body() ?: return
                val adapter = CountryAdapter(countries)
                binding.countriesList.adapter = adapter
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                val toast = Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
}