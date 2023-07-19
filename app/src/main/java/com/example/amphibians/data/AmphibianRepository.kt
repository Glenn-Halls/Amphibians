package com.example.amphibians.data

import com.example.amphibians.model.Amphibian
import com.example.amphibians.network.AmphibiansApiService

// Fetch Amphibian info from Amphibian API
interface AmphibianRepository {
    suspend fun getAmphibianInfo(): List<Amphibian>
}

class NetworkAmphibianRepository (
    private val amphibiansApiService: AmphibiansApiService
    ) : AmphibianRepository {
    // Fetches list of Amphibian info using AmphibianApi
    override suspend fun getAmphibianInfo(): List<Amphibian> = amphibiansApiService.getInfo()
    }
