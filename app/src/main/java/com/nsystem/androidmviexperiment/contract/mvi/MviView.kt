package com.nsystem.androidmviexperiment.contract.mvi

import io.reactivex.Observable

/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version MviView, v 0.0.1 11/10/20 14.49 by Putra Nugraha
 */
interface MviView<A: Action, S: State> {

    val actions: Observable<A>

    /**
     * render function is a must for View in MVI architecture
     */
    fun render(state: S)
}