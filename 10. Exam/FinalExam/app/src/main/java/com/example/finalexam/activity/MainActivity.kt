package com.example.finalexam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalexam.adapter.CryptoAdapter
import com.example.finalexam.databinding.ActivityMainBinding
import com.example.finalexam.db.AppDatabase
import com.example.finalexam.factory.CryptoViewModelFactory
import com.example.finalexam.repository.CryptoRepository
import com.example.finalexam.service.CryptoService
import com.example.finalexam.util.NetworkUtil
import com.example.finalexam.viewmodel.CryptoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    public lateinit var binding: ActivityMainBinding

    public lateinit var cryptoViewModel: CryptoViewModel

    public lateinit var db: RoomDatabase

    private lateinit var cryptoService: CryptoService

    private lateinit var cryptoRepository: CryptoRepository

    // move initialization in init() and make it lateinit ???
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/") // create constants in utils
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        observeData()

        if (!NetworkUtil.isConnected(this)) {
            Snackbar.make(
                binding.root,
                "No internet connection, information could be outdated",
                Snackbar.LENGTH_LONG
            ).show()
        }

        GlobalScope.launch {
            cryptoViewModel.getCryptos()
        }
    }

    private fun init() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "cryptos_database"
        ).build()

        val cryptoDao = (db as AppDatabase).cryptoDao()

        cryptoService = retrofit.create(CryptoService::class.java)

        cryptoRepository = CryptoRepository(this, cryptoService, cryptoDao)

        val cryptoViewModelFactory = CryptoViewModelFactory(cryptoRepository)

        cryptoViewModel =
            ViewModelProvider(this, cryptoViewModelFactory)[CryptoViewModel::class.java]
    }

    private fun observeData() {
        GlobalScope.launch {
            cryptoViewModel.cryptosList.collect {
                runOnUiThread {
                    val cryptos = it

                    val sortedCryptos = cryptos
                        .sortedByDescending { it.marketCap }
                        .sortedByDescending { it.favorite }

                    val adapter = CryptoAdapter(sortedCryptos)

                    binding.cryptosList.adapter = adapter
                    binding.tvCryptosCount.text = "Cryptos: ${it.size}"
                }
            }
        }
    }
}