package com.nsystem.androidmviexperiment.mvi.store

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.nsystem.androidmviexperiment.contract.mvi.*
import com.nsystem.androidmviexperiment.utils.withLatestFrom
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version Store, v 0.0.1 11/10/20 15.47 by Putra Nugraha
 */
class Store<A : Action, S : State>(
    private val reducer: Reducer<A, S>,
    private val middlewares: List<Middleware<A, S>>,
    private val uiScheduler: Scheduler,
    initialState: S
) {

    private val stateRelay = BehaviorRelay.createDefault<S>(initialState)

    private val actionsRelay = PublishRelay.create<A>()

    fun wire(): Disposable {
        return CompositeDisposable().apply {
            add(
                actionsRelay
                    .withLatestFrom(stateRelay) { action, state ->
                        reducer.reduce(state, action)
                    }
                    .distinctUntilChanged()
                    .subscribe(stateRelay::accept)
            )
            add(Observable.merge(middlewares.map {
                it.bind(actionsRelay, stateRelay)
            }).subscribe(actionsRelay::accept))
        }
    }

    fun bind(view: MviView<A, S>): Disposable {
        return CompositeDisposable().apply {
            add(stateRelay.observeOn(uiScheduler).subscribe(view::render))
            add(view.actions.subscribe(actionsRelay::accept))
        }
    }
}