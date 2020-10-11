package com.nsystem.androidmviexperiment.repository.api

import com.nsystem.androidmviexperiment.model.Champion
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version ChampionApi, v 0.0.1 11/10/20 15.41 by Putra Nugraha
 */
class ChampionApi : Api {

    override fun search(query: String): Observable<Champion> {
        return Observable.fromCallable {
            champions.first { it.name.contains(query, ignoreCase = true) }
        }.delay(2, TimeUnit.SECONDS)
    }

    override fun suggestions(query: String): Observable<List<String>> {
        return Observable.fromCallable {
            if (query.isBlank()) {
                emptyList()
            } else {
                champions.filter { it.name.contains(query, ignoreCase = true) }.map { it.name }
            }
        }
    }
}