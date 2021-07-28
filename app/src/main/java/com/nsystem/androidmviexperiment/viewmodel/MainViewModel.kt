package com.nsystem.androidmviexperiment.viewmodel

import androidx.lifecycle.viewModelScope
import com.nsystem.androidmviexperiment.model.Champion
import com.nsystem.androidmviexperiment.mvi.ChampionMviIntent
import com.nsystem.androidmviexperiment.mvi.ChampionReducer
import com.nsystem.androidmviexperiment.mvi.ChampionState
import com.nsystem.androidmviexperiment.mvi.LoadState
import com.nsystem.androidmviexperiment.repository.ChampionRepository
import com.nsystem.androidmviexperiment.repository.source.ChampionEntityRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version MainViewModel, v 0.0.1 11/10/20 15.34 by Putra Nugraha
 */
class MainViewModel: MviViewModel<ChampionState, ChampionMviIntent, ChampionReducer>(
    ChampionState.initial
) {

    private val championRepository: ChampionRepository = ChampionEntityRepository()

    override suspend fun executeIntent(mviIntent: ChampionMviIntent) {
        when (mviIntent) {
            is ChampionMviIntent.Search -> {
                handle(ChampionReducer.Loading)
                championRepository.search(mviIntent.query)
                    .catch { handle(ChampionReducer.LoadError("Error")) }
                    .onEach { handle(ChampionReducer.Loaded(it)) }
                    .launchIn(viewModelScope)
            }
            is ChampionMviIntent.LoadSuggestion -> {
                mviIntent.query
                        .takeIf { it.isNotBlank() }
                        ?.let { query ->
                            championRepository.suggestions(query)
                                .catch { handle(ChampionReducer.LoadError("Error")) }
                                .onEach { handle(ChampionReducer.SuggestionsLoaded(it)) }
                                .launchIn(viewModelScope)
                            }
            }
        }
    }

    override fun reduce(state: ChampionState, reducer: ChampionReducer): ChampionState {
        return when (reducer) {
            ChampionReducer.Loading -> state.copy(
                loadState = LoadState.LOADING,
                errorMessage = ""
            )
            is ChampionReducer.SuggestionsLoaded -> state.copy(
                loadState = LoadState.LOADED,
                championNames = reducer.championNames,
                errorMessage = ""
            )
            is ChampionReducer.Loaded -> state.copy(
                loadState = LoadState.LOADED,
                championNames = emptyList(),
                errorMessage = "",
                champion = reducer.champion
            )
            is ChampionReducer.LoadError -> state.copy(
                loadState = LoadState.ERROR,
                championNames = emptyList(),
                errorMessage = reducer.errorMessage,
                champion = Champion()
            )
        }
    }
}