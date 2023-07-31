package com.ibrahimethemsen.androidcryptotracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ibrahimethemsen.androidcryptotracker.R
import com.ibrahimethemsen.androidcryptotracker.databinding.FragmentHomeBinding
import com.ibrahimethemsen.androidcryptotracker.model.Difference
import com.ibrahimethemsen.androidcryptotracker.model.WebSocketState
import com.ibrahimethemsen.androidcryptotracker.network.restful.model.CryptoData
import com.ibrahimethemsen.androidcryptotracker.ui.adapter.CryptoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private var cryptoAdapter : CryptoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webSocket()
        viewModel.getAllCrypto()
        observe()
    }

    private fun observe(){
        viewModel.allCrypto.observe(viewLifecycleOwner){
            setAdapter(it)
        }
    }

    private fun setAdapter(cryptoList : CryptoData){
        cryptoAdapter = CryptoAdapter().apply {
            setCryptoList(cryptoList.data)
            binding.homeFragmentRv.adapter = this
        }
    }

    private fun webSocket(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        WebSocketState.Connected -> {
                            binding.btcPriceGroup.visibility = View.GONE
                            binding.btcProgressDescriptionTv.text = getString(R.string.ws_connected)
                        }
                        WebSocketState.Connecting -> {
                            binding.btcPriceGroup.visibility = View.GONE
                            binding.btcProgressDescriptionTv.text = getString(R.string.ws_connecting)
                        }
                        WebSocketState.Disconnected -> {
                            binding.btcPriceGroup.visibility = View.GONE
                            binding.btcProgress.visibility = View.GONE
                            binding.btcProgressDescriptionTv.text = getString(R.string.ws_disconnected)
                        }
                        WebSocketState.Disconnecting -> {
                            binding.btcPriceGroup.visibility = View.GONE
                            binding.btcProgress.visibility = View.GONE
                            binding.btcProgressDescriptionTv.text = getString(R.string.ws_disconnecting)
                        }
                        is WebSocketState.Error -> {
                            binding.btcProgressGroup.visibility = View.GONE
                            binding.btcPriceGroup.visibility = View.GONE
                            binding.btcErrorMessageTv.visibility = View.VISIBLE
                            binding.btcErrorMessageTv.text = getString(R.string.ws_error,it.error)
                        }
                        is WebSocketState.Price -> {
                            binding.btcProgressGroup.visibility = View.GONE
                            binding.btcPriceGroup.visibility = View.VISIBLE
                            setExchange(it.exchange)
                            binding.btcExchangeValueTv.text = it.exchangeValue.toString()
                            binding.btcPriceTv.text = it.value.toString()
                        }
                    }
                }
        }
    }

    private fun setExchange(difference: Difference) {
        when (difference) {
            Difference.UP -> {
                setExchangeEnum(R.drawable.arrow_up, R.color.green_color)
            }

            Difference.Down -> {
                setExchangeEnum(R.drawable.arrow_down, R.color.red_color)
            }

            Difference.Stable -> {
                setExchangeEnum(R.drawable.stable, R.color.gray_color)
            }
        }
    }

    private fun setExchangeEnum(@DrawableRes drawableRes: Int, @ColorRes colorRes: Int) {
        setImageViewExchangeDrawable(drawableRes)
        setExchangeTextColor(colorRes)
    }

    private fun setImageViewExchangeDrawable(@DrawableRes drawableRes: Int) {
        binding.btcExchangeIv.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                drawableRes
            )
        )
    }

    private fun setExchangeTextColor(@ColorRes colorRes: Int) {
        binding.btcExchangeValueTv.setTextColor(
            AppCompatResources.getColorStateList(
                requireContext(),
                colorRes
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        cryptoAdapter = null
    }
}