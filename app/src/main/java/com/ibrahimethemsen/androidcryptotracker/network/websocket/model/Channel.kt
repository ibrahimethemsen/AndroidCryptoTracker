package com.ibrahimethemsen.androidcryptotracker.network.websocket.model

data class Channel(
    val channel : String,
    val data : ChannelData,
    val event : String
){
    fun isTrade() : Boolean{
        return this.event == "trade"
    }
}
