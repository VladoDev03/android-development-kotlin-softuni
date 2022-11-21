package com.example.finalexam.repository

import android.content.Context
import com.example.finalexam.db.dao.CryptoDao
import com.example.finalexam.db.entity.CryptoDetails
import com.example.finalexam.model.CryptoDetailResponse
import com.example.finalexam.service.CryptoService
import com.example.finalexam.util.NetworkUtil

class CryptoRepository constructor(
    private val context: Context,
    private val cryptoService: CryptoService,
    private val cryptoDao: CryptoDao
) {
    suspend fun getCryptos(): List<CryptoDetails> {
        return try {
            if (NetworkUtil.isConnected(context)) { // check for internet connection
                val cryptos = cryptoService.getCryptos().execute().body()
                val savedCryptos = cryptos?.map { mapResponseToDbModel(it) }
                cryptoDao.insertAll(savedCryptos ?: arrayListOf())
            }

            cryptoDao.getCryptos()
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun getCryptoById(id: String): CryptoDetails? {
        return try {
            return cryptoDao.getCryptoById(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateCrypto(crypto: CryptoDetails) {
        cryptoDao.update(crypto)
    }

    private fun mapResponseToDbModel(response: CryptoDetailResponse): CryptoDetails {
        val a = CryptoDetails(
            uid = response.id,
            name = response.name,
            symbol = response.symbol,
            currentPrice = response.currentPrice.toString(),
            logo = response.image,
            marketCap = response.marketCap.toString(),
            favorite = false,
        )

        return a
    }
}