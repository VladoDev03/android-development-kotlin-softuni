package com.example.localstorage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.localstorage.R
import com.example.localstorage.databinding.CountryListItemBinding
import com.example.localstorage.fragments.CountryFragment
import com.example.localstorage.models.CountryInfo

class CountryAdapter(val countries: List<CountryInfo>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: CountryListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(layoutInflater, parent, false)

        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = countries[position]

        holder.binding.root.setOnClickListener {
            val activity = it.context as AppCompatActivity

            val transaction = activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, CountryFragment(currentCountry.name))
                .addToBackStack("first_transaction")

            transaction.commit()
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