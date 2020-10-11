package com.nsystem.androidmviexperiment.contract.mvi


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version Reducer, v 0.0.1 11/10/20 15.50 by Putra Nugraha
 */
interface Reducer<A: Action, S: State> {

    fun reduce(state: S, action: A): S
}