package com.nsystem.androidmviexperiment.mvi.store

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.nsystem.androidmviexperiment.contract.mvi.*
import com.nsystem.androidmviexperiment.utils.withLatestFrom
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


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
     * actions will be used by Middleware to execute the action of UiAction (Search or Load Suggestion)
     */
    private val actionsRelay = PublishRelay.create<A>()

    /**
     * Wire UiAction with respective Middleware, so Middleware may produce SearchAction (transform
     * UiAction to SearchAction)
     *
     * Setup actionsRelay to call reducer to produce State, after State returned from Reducer
     * it will emit stateRelay::accept (emit onNext in stateRelay)
     *
     * @return Disposable so ViewModel may be able to dispose Observables
     */
    fun wire(): Disposable {
        return CompositeDisposable().apply {
            add(Observable.merge(middlewares.map { // merge used to enable applying bind logic to each middlewares here
                it.bind(actionsRelay, stateRelay)
            }).subscribe(actionsRelay::accept))
            add(
                actionsRelay
                    .withLatestFrom(stateRelay) { action, state ->
                        reducer.reduce(state, action) // with latest from operator, action executed will emit the latest state emitted
                    }
                    .distinctUntilChanged()
                    .subscribe(stateRelay::accept)
            )
        }
    }

    /**
     * subscribe to action relay by call actionsRelay::accept (emit onNext in actionsRelay) based on
     * actions executed due to user input (UiAction)
     *
     * subscribe to view render based on latest emitted state in state relay
     */
    fun bind(view: MviView<A, S>): Disposable {
        return CompositeDisposable().apply {
            add(view.actions.subscribe(actionsRelay::accept))
            add(stateRelay.observeOn(uiScheduler).subscribe(view::render))
        }
    }
}