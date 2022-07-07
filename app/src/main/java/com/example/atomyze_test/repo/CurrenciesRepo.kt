package com.example.atomyze_test.repo

import com.example.atomyze_test.MainContract
import com.example.atomyze_test.MainRepo
import io.reactivex.Single
import javax.inject.Inject

class CurrenciesRepo @Inject constructor(
    private val currenciesAPI: CurrenciesAPI,
) : MainRepo {

    override fun getCurrencies(): Single<List<MainContract.Currency>> {
        return currenciesAPI.getCurrencies().map {
            it.currencies.map { (code, name, rate) ->
                MainContract.Currency(code, name, rate)
            }
        }
    }

}