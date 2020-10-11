package com.nsystem.androidmviexperiment.mvi.reducer

import com.nsystem.androidmviexperiment.contract.mvi.Action
import com.nsystem.androidmviexperiment.contract.mvi.Reducer
import com.nsystem.androidmviexperiment.mvi.intent.SearchAction
import com.nsystem.androidmviexperiment.mvi.state.UiState
import com.nsystem.androidmviexperiment.view.UiAction


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version SearchReducer, v 0.0.1 11/10/20 16.14 by Putra Nugraha
 */
class SearchReducer: Reducer<Action, UiState> {

    override fun reduce(state: UiState, action: Action): UiState {
        return when (action) {
            SearchAction.SearchLoadingAction        -> state.copy(loading = true, error = null, suggestions = emptyList())
            is SearchAction.SearchSuccessAction     -> state.copy(
                loading = false,
                data = action.data,
                error = null,
                suggestions = emptyList()
            )
            is SearchAction.SearchFailureAction     -> state.copy(loading = false, error = action.error)
            is SearchAction.SuggestionsLoadedAction -> state.copy(suggestions = action.suggestions)
            is UiAction.LoadSuggestionsAction,
            is UiAction.SearchAction                  -> state
            else                                      -> state
        }
    }
}