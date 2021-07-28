package com.nsystem.androidmviexperiment.mvi

import com.nsystem.androidmviexperiment.contract.mvi.State
import com.nsystem.androidmviexperiment.model.Champion

data class ChampionState(
    val loadState: LoadState,
    val champion: Champion,
    val championNames: List<String>,
    val errorMessage: CharSequence,
): State {

    companion object {

        val initial = ChampionState(
            loadState = LoadState.IDLE,
            champion = Champion(),
            championNames = emptyList(),
            errorMessage = "",
        )
    }
}