package com.example.amphibians.network

import com.example.amphibians.model.Amphibian
import retrofit2.http.GET


//  Public interface to expose getInfo()
interface AmphibiansApiService {
    // HTTP get request using "amphibians" as endpoint
    @GET("amphibians")
    suspend fun getInfo(): List<Amphibian>
}
