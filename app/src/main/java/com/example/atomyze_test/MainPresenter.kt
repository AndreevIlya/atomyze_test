package com.example.atomyze_test

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val repo: MainRepo,
) : MainContract.Presenter() {

    override val needsUpdateLiveData = MutableLiveData(false)

    private lateinit var disposable: Disposable

    private var state: State = State.NOT_LOADED

    private val executorsService: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
        executorsService.execute {
            while (true) {
                Thread.sleep(10000)
                if (state != State.LOADING) {
                    needsUpdateLiveData.postValue(true)
                }
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        executorsService.shutdownNow()
    }

    private enum class State {
        NOT_LOADED, LOADING, LOADED, FAILED
    }

}