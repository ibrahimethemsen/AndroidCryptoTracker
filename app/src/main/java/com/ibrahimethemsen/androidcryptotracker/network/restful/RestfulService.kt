package com.ibrahimethemsen.androidcryptotracker.network.restful

import com.ibrahimethemsen.androidcryptotracker.network.restful.model.CryptoData
import retrofit2.http.GET

interface RestfulService {
    @GET("v2/assets")
    suspend fun getAllCrypto() : CryptoData
}