package com.nsystem.androidmviexperiment.view

import com.nsystem.androidmviexperiment.contract.mvi.Action


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version UiAction, v 0.0.1 11/10/20 15.24 by Putra Nugraha
 */
/**
 * UiAction meant for Intent (Flow that sent from View to Model)
 */
sealed class UiAction: Action {

    /**
     * Search Action is Intent for searching the value and later will display the value if found
     * SearchAction will be inherit UiAction
     */
    class SearchAction(val query: String) : UiAction()

    /**
     * Load Suggestion Intent for showing list of suggestions (based on user input)
     * LoadSuggestionsAction will be inherit UiAction
     */
    class LoadSuggestionsAction(val query: String) : UiAction()
}