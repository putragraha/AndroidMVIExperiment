package com.nsystem.androidmviexperiment.mvi

import com.nsystem.androidmviexperiment.contract.mvi.MviIntent

sealed class ChampionMviIntent: MviIntent {

    data class Search(val query: CharSequence): ChampionMviIntent()

    data class LoadSuggestion(val query: CharSequence): ChampionMviIntent()
}