package com.example.finalexam.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalexam.R
import com.example.finalexam.activity.MainActivity
import com.example.finalexam.databinding.CryptoListItemBinding
import com.example.finalexam.db.entity.CryptoDetails
import com.example.finalexam.fragment.CryptoDetailsFragment

class CryptoAdapter(private val cryptos: List<CryptoDetails>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    class CryptoViewHolder(val binding: CryptoListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CryptoListItemBinding.inflate(layoutInflater, parent, false)

        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val currentCrypto = cryptos[position]

        holder.binding.apply {
            crypto = currentCrypto.name
            symbol = currentCrypto.symbol
            price = "${currentCrypto.currentPrice} USD"

            Glide
                .with(root.context)
                .load(currentCrypto.logo)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivLogo)

            holder.binding.root.setOnClickListener {
                (it.context as MainActivity).supportFragmentManager.commit {
                    val bundle = Bundle()
                    bundle.putString("crypto_id", currentCrypto.uid)
                    replace(R.id.container_view, CryptoDetailsFragment::class.java, bundle)
                    addToBackStack("cryptos_list_to_details")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cryptos.size
    }
}