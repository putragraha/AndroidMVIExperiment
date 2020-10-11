package com.nsystem.androidmviexperiment.view

import androidx.lifecycle.ViewModel
import com.nsystem.androidmviexperiment.contract.mvi.Action
import com.nsystem.androidmviexperiment.contract.mvi.MviView
import com.nsystem.androidmviexperiment.mvi.middleware.SearchMiddleware
import com.nsystem.androidmviexperiment.mvi.middleware.SuggestionsMiddleware
import com.nsystem.androidmviexperiment.mvi.reducer.SearchReducer
import com.nsystem.androidmviexperiment.repository.api.Api
import com.nsystem.androidmviexperiment.repository.api.ChampionApi
import com.nsystem.androidmviexperiment.mvi.state.UiState
import com.nsystem.androidmviexperiment.mvi.store.Store
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version MainViewModel, v 0.0.1 11/10/20 15.34 by Putra Nugraha
 */
class MainViewModel: ViewModel() {

    private val api: Api = ChampionApi()

    private val uiScheduler = AndroidSchedulers.mainThread()

    private val store: Store<Action, UiState> = Store(
        SearchReducer(),
        listOf(
            SearchMiddleware(api, uiScheduler),
            SuggestionsMiddleware(api, uiScheduler)
        ),
        uiScheduler,
        UiState()
    )

    private val disposable = store.wire()

    private var viewBinding: Disposable? = null

    override fun onCleared() {
        disposable.dispose()
    }

    fun bind(view: MviView<Action, UiState>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
    }
}