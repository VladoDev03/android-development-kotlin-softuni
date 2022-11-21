package com.example.finalexam.fragment

import android.os.Bundle
import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.finalexam.R
import com.example.finalexam.activity.MainActivity
import com.example.finalexam.databinding.FragmentCryptoDetailsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CryptoDetailsFragment : Fragment() {
    private lateinit var binding:FragmentCryptoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedCryptoId = arguments?.getString("crypto_id", null)

        GlobalScope.launch {
            (activity as? MainActivity)?.cryptoViewModel?.getCryptoById(
                selectedCryptoId ?: return@launch
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCryptoDetailsBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        GlobalScope.launch {
            (activity as? MainActivity)?.cryptoViewModel?.selectedCrypto?.collect {
                binding.crypto = it ?: return@collect

                (activity as? MainActivity)?.runOnUiThread {
                    setDataBinding()

                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it.logo)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivLogo)
                }
            }
        }
    }

    private fun setDataBinding() {
        binding.crypto ?: return

        if (binding.crypto?.favorite == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        binding.btnLike.setOnClickListener {
            val crypto = binding.crypto
            crypto?.favorite = crypto?.favorite != true

            if (crypto?.favorite == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            GlobalScope.launch {
                (activity as? MainActivity)?.cryptoViewModel?.updateFavorites(
                    crypto ?: return@launch
                )
            }
        }
    }
}