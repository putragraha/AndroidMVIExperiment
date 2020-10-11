package com.nsystem.androidmviexperiment.contract.mvi

import io.reactivex.Observable


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version Middleware, v 0.0.1 11/10/20 15.50 by Putra Nugraha
 */
interface Middleware<A: Action, S: State> {

    /**
     * @method bind
     * execute chains of actions
     */
    fun bind(actions: Observable<A>, state: Observable<S>): Observable<A>
}