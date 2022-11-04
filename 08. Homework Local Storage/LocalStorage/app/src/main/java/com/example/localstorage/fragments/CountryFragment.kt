package com.example.localstorage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.localstorage.CountryDatabase
import com.example.localstorage.R
import com.example.localstorage.databinding.FragmentCountryBinding
import com.example.localstorage.models.CountryInfo
import com.example.localstorage.repositories.CountryRepository
import com.example.localstorage.services.CountryService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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

                val db = Room.databaseBuilder(
                    context!!,
                    CountryDatabase::class.java,
                    "countries_db"
                ).build()

                val countryDao = db.countryDao()

                GlobalScope.async {
                    val selectedCountry = countryDao.getByName(countryName)

                    binding.apply {
                        country = selectedCountry[0].name
                        capital = selectedCountry[0].capital
                        population = selectedCountry[0].population.toString()
                        region = selectedCountry[0].region
                        area = selectedCountry[0].area.toInt().toString()
//                        binding.ivFlag.setImageResource(R.drawable.ic_launcher_foreground)
                    }
                }
            }
        })
    }
}