package com.nsystem.androidmviexperiment.mvi

import com.nsystem.androidmviexperiment.contract.mvi.Reducer
import com.nsystem.androidmviexperiment.model.Champion

sealed class ChampionReducer: Reducer {

    object Loading : ChampionReducer()

    data class Loaded(val champion: Champion): ChampionReducer()

    data class SuggestionsLoaded(val championNames: List<String>): ChampionReducer()

    data class LoadError(val errorMessage: CharSequence): ChampionReducer()
}