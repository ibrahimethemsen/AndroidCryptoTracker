package com.ibrahimethemsen.androidcryptotracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahimethemsen.androidcryptotracker.network.restful.RestfulService
import com.ibrahimethemsen.androidcryptotracker.network.restful.model.CryptoData
import com.ibrahimethemsen.androidcryptotracker.network.websocket.BtcWebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    btcWebSocket: BtcWebSocket,
    private val restfulService: RestfulService
) : ViewModel() {
    val state = btcWebSocket.events

    private val _allCrypto = MutableLiveData<CryptoData>()
    val allCrypto : LiveData<CryptoData> = _allCrypto

    fun getAllCrypto(){
        viewModelScope.launch {
            _allCrypto.postValue(restfulService.getAllCrypto())
        }
    }
}