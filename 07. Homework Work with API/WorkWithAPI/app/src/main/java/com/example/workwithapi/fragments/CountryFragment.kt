package com.example.workwithapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.workwithapi.R
import com.example.workwithapi.databinding.FragmentCountryBinding
import com.example.workwithapi.models.Country
import com.example.workwithapi.models.CountryInfo
import com.example.workwithapi.repositories.CountryRepository
import com.example.workwithapi.services.CountryService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryFragment(val countryName: String) : Fragment() {
    lateinit var binding: FragmentCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
            activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.commit()
        }

        setCountry()

        return binding.root
    }

    fun setCountry() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()) // Not required
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountryRepository(countryService)

        println("https://restcountries.com/v2/name/${countryName}")

        countryRepository.getCountry(countryName)?.enqueue(object: Callback<List<CountryInfo>> {
            override fun onResponse(call: Call<List<CountryInfo>>, response: Response<List<CountryInfo>>) {
                val selectedCountry = response.body() ?: return

//                val toast = Toast.makeText(binding.root.context, selectedCountry[0].name, Toast.LENGTH_SHORT)
//                toast.show()
//
//                printCountry(selectedCountry[0])

                binding.apply {
                    country = selectedCountry[0].name
                    capital = selectedCountry[0].capital
                    population = selectedCountry[0].population.toString()
                    region = selectedCountry[0].region
                    area = selectedCountry[0].area.toInt().toString()

                    Glide
                        .with(root.context)
                        .load(selectedCountry[0].flags.png)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(ivFlag)
                }
            }

            override fun onFailure(call: Call<List<CountryInfo>>, t: Throwable) {
                val toast = Toast.makeText(binding.root.context, "Failed", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

    fun printCountry(selectedCountry: CountryInfo) {
        println("Name => ${selectedCountry.name}")
        println("Area => ${selectedCountry.area}")
        println("Capital => ${selectedCountry.capital}")
        println("Population => ${selectedCountry.population}")
        println("Region => ${selectedCountry.region}")
    }
}