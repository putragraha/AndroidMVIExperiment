package com.nsystem.androidmviexperiment.repository.source

import com.nsystem.androidmviexperiment.repository.ChampionRepository

class ChampionEntityRepository: ChampionRepository {

    private val championEntityData by lazy {
        ChampionMockData()
    }

    override suspend fun search(query: CharSequence) = championEntityData.search(query)

    override suspend fun suggestions(query: CharSequence) = championEntityData.suggestions(query)
}