package com.example.atomyze_test

import io.reactivex.Single

interface MainRepo {
    fun getCurrencies(): Single<List<MainContract.Currency>>
}