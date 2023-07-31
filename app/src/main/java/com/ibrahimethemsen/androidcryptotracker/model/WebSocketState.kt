package com.ibrahimethemsen.androidcryptotracker.model

sealed class WebSocketState{
    object Connecting : WebSocketState()
    object Connected : WebSocketState()
    data class Price(val value : Double,val  exchange : Difference,val exchangeValue : Double) : WebSocketState()
    object Disconnecting : WebSocketState()
    object Disconnected : WebSocketState()
    data class Error(val error : String) : WebSocketState()
}

enum class Difference{
    UP,
    Down,
    Stable
}