package com.ibrahimethemsen.androidcryptotracker.network.restful.model


import com.squareup.moshi.Json

data class CryptoData(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "timestamp")
    val timestamp: Long
)