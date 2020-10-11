package com.nsystem.androidmviexperiment.mvi.intent

import com.nsystem.androidmviexperiment.contract.mvi.Action
import com.nsystem.androidmviexperiment.model.Champion


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version SearchAction, v 0.0.1 11/10/20 16.16 by Putra Nugraha
 */
sealed class SearchAction : Action {

    object SearchLoadingAction : SearchAction()

    class SearchSuccessAction(val data: Champion) : SearchAction()

    class SearchFailureAction(val error: Throwable) : SearchAction()

    class SuggestionsLoadedAction(val suggestions: List<String>) : SearchAction()
}