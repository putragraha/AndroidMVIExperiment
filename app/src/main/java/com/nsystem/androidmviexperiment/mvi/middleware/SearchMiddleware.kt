package com.nsystem.androidmviexperiment.mvi.middleware

import com.nsystem.androidmviexperiment.contract.mvi.Action
import com.nsystem.androidmviexperiment.contract.mvi.Middleware
import com.nsystem.androidmviexperiment.mvi.intent.SearchAction
import com.nsystem.androidmviexperiment.mvi.state.UiState
import com.nsystem.androidmviexperiment.repository.api.Api
import com.nsystem.androidmviexperiment.utils.withLatestFrom
import com.nsystem.androidmviexperiment.view.UiAction
import io.reactivex.Observable
import io.reactivex.Scheduler


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version SearchMiddleware, v 0.0.1 11/10/20 16.23 by Putra Nugraha
 */
/**
 * These Middleware was to execute operations for Intent/Action {@link SearchAction}
 */
class SearchMiddleware(
    private val api: Api,
    private val uiScheduler: Scheduler
): Middleware<Action, UiState> {

    override fun bind(actions: Observable<Action>, state: Observable<UiState>): Observable<Action> {
        return actions.ofType(UiAction.SearchAction::class.java)
            .withLatestFrom(state) { action, currentState -> action to currentState }
            .flatMap { (action, _) ->
                api.search(action.query)
                    .map<SearchAction> { result ->
                        SearchAction.SearchSuccessAction(result)
                    }
                    .onErrorReturn { e -> SearchAction.SearchFailureAction(e) }
                    .observeOn(uiScheduler)
                    .startWith(SearchAction.SearchLoadingAction)
            }
    }
}