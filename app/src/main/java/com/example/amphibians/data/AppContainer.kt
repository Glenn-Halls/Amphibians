package com.example.amphibians.data

import com.example.amphibians.network.AmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibianRepository: AmphibianRepository
}

class DefaultAppContainer : AppContainer {

    // Base URL for fetching data
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com"

    // Retrofit builder creates object using kotlinx serialisation converter
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Retrofit object for creating API calls
    private val retrofitService: AmphibiansApiService by lazy {
        retrofit.create(AmphibiansApiService::class.java)
    }

    // DI implementation for Amphibian data repository
    override val amphibianRepository: AmphibianRepository by lazy {
        NetworkAmphibianRepository(retrofitService)
    }

}
