package com.example.localstorage.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.localstorage.CountryDatabase
import com.example.localstorage.adapters.CountryAdapter
import com.example.localstorage.databinding.ActivityMainBinding
import com.example.localstorage.entities.Country
import com.example.localstorage.models.CountryInfo
import com.example.localstorage.models.Flag
import com.example.localstorage.repositories.CountryRepository
import com.example.localstorage.services.CountryService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isOnline: Boolean = isOnline(this)

        val db = Room.databaseBuilder(
            applicationContext,
            CountryDatabase::class.java,
            "countries_db"
        ).build()

        val countryDao = db.countryDao()

        if (!isOnline) {
            GlobalScope.async {
                val countries = countryDao.getAll()
                val countriesToAdd: ArrayList<CountryInfo> = ArrayList()

                for (it in countries) {
                    val country: CountryInfo = CountryInfo(
                        it.name,
                        if (!it.capital.isNullOrEmpty()) it.capital else "no information",
                        it.region,
                        it.population,
                        it.area,
                        Flag("", "")
//                        Flag("", it.pngFlag)
                    )

                    countriesToAdd.add(country)
                }

                val adapter = CountryAdapter(countriesToAdd)
                binding.countriesList.adapter = adapter
            }

            val snackbar = Snackbar
                .make(binding.root, "You are offline and the data is possible to be outdated", Snackbar.LENGTH_LONG)
            snackbar.show()
        } else {
            val retrofit = Retrofit
                .Builder()
                .baseUrl("https://restcountries.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient()) // Not required
                .build()

            val countryService = retrofit.create(CountryService::class.java)
            val countryRepository = CountryRepository(countryService)

            countryRepository.getCountries()?.enqueue(object: Callback<List<CountryInfo>> {
                override fun onResponse(call: Call<List<CountryInfo>>, response: Response<List<CountryInfo>>) {
                    val countries = response.body() ?: return
                    val adapter = CountryAdapter(countries)
                    binding.countriesList.adapter = adapter

                    val countriesToAdd: ArrayList<Country> = ArrayList()

                    var id: Int = 1

                    for (it in countries) {
                        val country: Country = Country(
                            id,
                            it.name,
                            (if (!it.capital.isNullOrEmpty()) it.capital else "no information") as String,
                            it.region,
                            it.population,
                            it.area,
//                            it.flags.png
                        )

                        countriesToAdd.add(country)
                        id++
                    }

                    println("Success")

                    GlobalScope.async {
                        if (countryDao.getAll().size > 0) {
                            countryDao.deleteAll()
                        }

                        countryDao.insertMany(countriesToAdd)
                    }
                }

                override fun onFailure(call: Call<List<CountryInfo>>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT)
                    toast.show()
                }
            })
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }

        return false
    }
}