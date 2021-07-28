package com.nsystem.androidmviexperiment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsystem.androidmviexperiment.contract.mvi.MviIntent
import com.nsystem.androidmviexperiment.contract.mvi.Reducer
import com.nsystem.androidmviexperiment.contract.mvi.State
import kotlinx.coroutines.flow.*

abstract class MviViewModel<S: State, I: MviIntent, R: Reducer>(
    initialState: S
): ViewModel() {

    companion object {

        private const val FLOW_BUFFER_CAPACITY = 64
    }

    private val stateFlow = MutableStateFlow(initialState)

    val state: StateFlow<S> = stateFlow.asStateFlow()

    private val intentFlow = MutableSharedFlow<I>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)

    private val reduceFlow = MutableSharedFlow<R>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)

    init {
        intentFlow
            .onEach { intent ->
                executeIntent(intent)
            }
            .launchIn(viewModelScope)
        reduceFlow
            .onEach { action ->
                stateFlow.value = reduce(stateFlow.value, action)
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(mviIntent: I) {
        intentFlow.tryEmit(mviIntent)
    }

    protected fun handle(reduceAction: R) {
        reduceFlow.tryEmit(reduceAction)
    }

    protected abstract suspend fun executeIntent(mviIntent: I)

    protected abstract fun reduce(state: S, reducer: R): S
}