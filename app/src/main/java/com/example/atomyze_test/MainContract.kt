package com.example.atomyze_test

import androidx.lifecycle.LiveData
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainContract {

    interface View: MvpView{
        @AddToEndSingle
        fun showLoading()
        @AddToEndSingle
        fun showCurrencies(currencies: List<Currency>)
        @AddToEndSingle
        fun showError()
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract val needsUpdateLiveData: LiveData<Boolean>

        abstract fun updateCurrencies()
    }

    data class Currency(
        val code: String,
        val name: String,
        val rate: Float
    )
}