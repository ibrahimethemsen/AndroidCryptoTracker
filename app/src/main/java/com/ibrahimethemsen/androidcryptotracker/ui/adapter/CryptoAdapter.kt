package com.ibrahimethemsen.androidcryptotracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibrahimethemsen.androidcryptotracker.databinding.AdapterItemBinding
import com.ibrahimethemsen.androidcryptotracker.network.restful.model.Data

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {
    private val cryptoList = mutableListOf<Data>()

    fun setCryptoList(newList : List<Data>){
        cryptoList.addAll(newList)
    }

    class CryptoViewHolder(private val binding : AdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem : Data){
            binding.apply {
                adapterPriceName.text = listItem.name
                listItem.explorer?.let {
                    adapterWebSiteUrlTv.text = it.substringAfter("://").substringBefore("/")
                }
                adapterPriceSymbol.text = listItem.symbol
                adapterPriceTv.text = String.format("%.2f",listItem.priceUsd.toDouble())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = AdapterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoViewHolder(binding)
    }

    override fun getItemCount(): Int = cryptoList.size

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
         holder.bind(cryptoList[position])
    }

}
