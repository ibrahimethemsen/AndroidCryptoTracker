package com.ibrahimethemsen.androidcryptotracker.network.websocket.model

data class Subscription(
    val event : String,
    val data : SubscriptionData = SubscriptionData()
)