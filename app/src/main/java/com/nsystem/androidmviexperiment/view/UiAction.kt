package com.nsystem.androidmviexperiment.view

import com.nsystem.androidmviexperiment.contract.mvi.Action


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version UiAction, v 0.0.1 11/10/20 15.24 by Putra Nugraha
 */
sealed class UiAction: Action {

    class SearchAction(val query: String) : UiAction()

    class LoadSuggestionsAction(val query: String) : UiAction()
}