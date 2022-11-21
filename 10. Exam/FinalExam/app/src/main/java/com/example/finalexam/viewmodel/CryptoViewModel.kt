package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.finalexam.db.entity.CryptoDetails
import com.example.finalexam.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CryptoViewModel(
    private val cryptoRepository: CryptoRepository
) : ViewModel() {

    private val cryptoListStateFlow = MutableStateFlow<List<CryptoDetails>>(arrayListOf())
    private val selectedCryptoStateFlow = MutableStateFlow<CryptoDetails?>(null)

    val cryptosList: StateFlow<List<CryptoDetails>> = cryptoListStateFlow.asStateFlow()
    val selectedCrypto: StateFlow<CryptoDetails?> = selectedCryptoStateFlow.asStateFlow()

    suspend fun getCryptos() {
        val cryptos = cryptoRepository.getCryptos()
        cryptoListStateFlow.value = cryptos
    }

    suspend fun getCryptoById(id: String) {
        val crypto = cryptoRepository.getCryptoById(id)
        selectedCryptoStateFlow.value = crypto ?: return
    }

    suspend fun updateFavorites(crypto: CryptoDetails) {
        cryptoRepository.updateCrypto(crypto)

        selectedCryptoStateFlow.value =
            selectedCryptoStateFlow.value?.copy(favorite = crypto.favorite)
    }
}