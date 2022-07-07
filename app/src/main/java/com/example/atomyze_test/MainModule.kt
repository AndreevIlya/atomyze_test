package com.example.atomyze_test

import com.example.atomyze_test.repo.CurrenciesAPI
import com.example.atomyze_test.repo.CurrenciesRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MainModule {

    @Binds
    abstract fun presenter(presenter: MainPresenter): MainContract.Presenter

    @Binds
    abstract fun repo(repo: CurrenciesRepo): MainRepo

    companion object {

        @Provides
        fun currenciesAPI(factory: CurrenciesAPI.Factory) = factory.create()

    }

}