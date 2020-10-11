package com.nsystem.androidmviexperiment.mvi.state

import com.nsystem.androidmviexperiment.contract.mvi.State
import com.nsystem.androidmviexperiment.model.Champion


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version UiState, v 0.0.1 11/10/20 14.52 by Putra Nugraha
 */
data class UiState(
    val loading: Boolean = false,
    val data: Champion? = null,
    val error: Throwable? = null,
    val suggestions: List<String>? = null
) : State