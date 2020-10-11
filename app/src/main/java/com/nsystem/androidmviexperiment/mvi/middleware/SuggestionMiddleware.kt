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
 * @version SuggestionMiddleware, v 0.0.1 11/10/20 18.05 by Putra Nugraha
 */
class SuggestionsMiddleware(private val api: Api, private val uiScheduler: Scheduler) :
    Middleware<Action, UiState> {

    override fun bind(actions: Observable<Action>, state: Observable<UiState>): Observable<Action> {
        return actions.ofType(UiAction.LoadSuggestionsAction::class.java)
            .withLatestFrom(state) { action, currentState -> action to currentState }
            .switchMap { (action, _) ->
                api.suggestions(action.query)
                    .onErrorReturnItem(emptyList())
                    .map { result -> SearchAction.SuggestionsLoadedAction(result) }
                    .observeOn(uiScheduler)
            }
    }
}