package com.example.atomyze_test.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atomyze_test.MainContract
import com.example.atomyze_test.databinding.CurrencyCardBinding

class CurrenciesAdapter : RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>() {

    lateinit var currencies: List<MainContract.Currency>

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
        }
    }

    override fun getItemCount(): Int = currencies.size

}