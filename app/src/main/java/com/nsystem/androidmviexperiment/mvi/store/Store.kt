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

    /**
     * It's using relay to enable continuous stream, as relay works as Observable and Consumer, and only have onNext
     * state will be passed to Reducer to be defined which state is currently will be rendered to view
     */
    private val stateRelay = BehaviorRelay.createDefault<S>(initialState)

    /**
     * It's using relay to enable continuous stream, as relay works as Observable and Consumer, and only have onNext
     * actions will be used by Middleware to execute the action (Search or Load Suggestion)
     */
    private val actionsRelay = PublishRelay.create<A>()

    /**
     * Chaining stateRelay and actionRelay with operators here, and get the disposable to be able for disposed in ViewModel
     */
    fun wire(): Disposable {
        return CompositeDisposable().apply {
            add(
                actionsRelay
                    .withLatestFrom(stateRelay) { action, state ->
                        reducer.reduce(state, action) // with latest from operator, action executed will emit the latest state emitted
                    }
                    .distinctUntilChanged()
                    .subscribe(stateRelay::accept)
            )
            add(Observable.merge(middlewares.map { // merge used to enable applying bind logic to each middlewares here
                it.bind(actionsRelay, stateRelay)
            }).subscribe(actionsRelay::accept))
        }
    }

    /**
     * subsribing to action relay based on actions executed due to user input
     * subscribe to view render based on latest emitted state in state relay
     */
    fun bind(view: MviView<A, S>): Disposable {
        return CompositeDisposable().apply {
            add(stateRelay.observeOn(uiScheduler).subscribe(view::render))
            add(view.actions.subscribe(actionsRelay::accept))
        }
    }
}