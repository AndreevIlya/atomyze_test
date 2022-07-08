package com.example.atomyze_test.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atomyze_test.MainContract
import com.example.atomyze_test.databinding.CurrencyCardBinding

class CurrenciesAdapter : RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>() {

    lateinit var currencies: List<MainContract.Currency>

    var exchangeValue: Float? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CurrencyViewHolder(val binding: CurrencyCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::CurrencyViewHolder)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.binding.run {
            code.text = currency.code
            name.text = currency.name.lowercase()
            rate.text = currency.rate.toString()
            exchangeValue?.let {
                exchangeTitle.visibility = View.VISIBLE
                exchange.visibility = View.VISIBLE
                exchange.text = (exchangeValue!! / currency.rate).toString()
            } ?: let {
                exchangeTitle.visibility = View.GONE
                exchange.visibility = View.GONE
                exchange.text = ""
            }
        }
    }

    override fun getItemCount(): Int = currencies.size

}