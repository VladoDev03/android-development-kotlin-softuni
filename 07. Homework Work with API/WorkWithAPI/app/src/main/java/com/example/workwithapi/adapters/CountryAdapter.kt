package com.example.workwithapi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workwithapi.R
import com.example.workwithapi.databinding.CountryListItemBinding
import com.example.workwithapi.models.Country

class CountryAdapter(val countries: List<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: CountryListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(layoutInflater, parent, false)

        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = countries[position]

        holder.binding.root.setOnClickListener {
            val country: Country = countries[position]
            println("https://restcountries.com/v2/name/${country.name}")
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
}