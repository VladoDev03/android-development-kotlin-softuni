package com.example.workwithapi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workwithapi.R
import com.example.workwithapi.activities.MainActivity
import com.example.workwithapi.databinding.CountryListItemBinding
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

class CountryAdapter(val countries: List<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: CountryListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(layoutInflater, parent, false)

        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = countries[position]

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()) // Not required
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountryRepository(countryService)

        holder.binding.root.setOnClickListener {
            val country: Country = countries[position]
//            println("https://restcountries.com/v2/name/${country.name}")

            countryRepository.getCountry(country.name)?.enqueue(object: Callback<List<CountryInfo>> {
                override fun onResponse(call: Call<List<CountryInfo>>, response: Response<List<CountryInfo>>) {
                    val selectedCountry = response.body() ?: return
//                    val toast = Toast.makeText(holder.binding.root.context, selectedCountry[0].name, Toast.LENGTH_SHORT)
//                    toast.show()
//                    printCountry(selectedCountry[0])
                }

                override fun onFailure(call: Call<List<CountryInfo>>, t: Throwable) {
                    val toast = Toast.makeText(holder.binding.root.context, "Failed", Toast.LENGTH_SHORT)
                    toast.show()
                }
            })
        }

        holder.binding.apply {
            country = currentCountry.name
            capital = currentCountry.capital

            Glide
                .with(root.context)
                .load(currentCountry.flags.png)
                .centerCrop() // Not required
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivFlag)
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }

//    fun printCountry(selectedCountry: CountryInfo) {
//        println("Name => ${selectedCountry.name}")
//        println("Area => ${selectedCountry.area}")
//        println("Capital => ${selectedCountry.capital}")
//        println("Population => ${selectedCountry.population}")
//        println("Region => ${selectedCountry.region}")
//    }
}