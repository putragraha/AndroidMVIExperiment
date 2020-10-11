package com.nsystem.androidmviexperiment.repository.api

import com.nsystem.androidmviexperiment.model.Champion
import io.reactivex.Observable


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version Api, v 0.0.1 11/10/20 15.40 by Putra Nugraha
 */
interface Api {

    fun search(q: String): Observable<Champion>

    fun suggestions(query: String): Observable<List<String>>
}