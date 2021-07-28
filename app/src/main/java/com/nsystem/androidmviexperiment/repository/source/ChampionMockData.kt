package com.nsystem.androidmviexperiment.repository.source

import android.util.Log
import com.nsystem.androidmviexperiment.model.Champion
import com.nsystem.androidmviexperiment.repository.ChampionEntityData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version ChampionApi, v 0.0.1 11/10/20 15.41 by Putra Nugraha
 */
class ChampionMockData: ChampionEntityData {

    override suspend fun search(query: CharSequence): Flow<Champion> {
        delay(2_000)
        return flow {
            val champion = champions.first {
                it.name.contains(query, ignoreCase = true)
            }

            emit(champion)
        }
    }

    override suspend fun suggestions(query: CharSequence): Flow<List<String>> {
        return flow {
            val championNames = if (query.isBlank()) {
                emptyList()
            } else {
                champions
                    .filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                    .map { it.name }
            }

            Log.e("TAG", "suggestions: $championNames")
            emit(championNames)
        }
    }
}