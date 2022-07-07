package com.example.atomyze_test

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val repo: MainRepo,
) : MainContract.Presenter() {

    private lateinit var disposable: Disposable

    private var state: State = State.NOT_LOADED

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    override fun updateCurrencies() = load()

    private fun load() {
        state = State.LOADING
        viewState.showLoading()
        repo.getCurrencies()
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    state = State.LOADED
                    viewState.showCurrencies(it)
                }
            ) {
                state = State.FAILED
                viewState.showError()
            }
            .also { disposable = it }
    }

    private enum class State {
        NOT_LOADED, LOADING, LOADED, FAILED
    }

}