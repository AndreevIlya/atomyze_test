package com.example.atomyze_test

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.atomyze_test.databinding.MainActivityBinding
import com.example.atomyze_test.ui.CurrenciesAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : MvpAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenterProvider: Provider<MainContract.Presenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val adapter = CurrenciesAdapter()

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MainApplication).mainComponent.injectMain(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView<MainActivityBinding>(this, R.layout.main_activity)
            .apply {
                updateButton.setOnClickListener { presenter.updateCurrencies() }
                exchangeButton.setOnClickListener {
                    val value = exchange.text.toString()
                    adapter.exchangeValue = if (value.isEmpty()) null else value.toFloat()
                }
            }
    }

    override fun showCurrencies(currencies: List<MainContract.Currency>) {
        binding.run {
            updateButton.visibility = View.VISIBLE
            loading.visibility = View.GONE
            this.currencies.adapter = adapter.apply { this.currencies = currencies }
        }
    }

    override fun showLoading() {
        binding.run {
            updateButton.visibility = View.GONE
            loading.visibility = View.VISIBLE
        }
    }

    override fun showError() {
        Snackbar.make(binding.root, getString(R.string.error_message), LENGTH_SHORT).show()
    }
}