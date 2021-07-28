package com.nsystem.androidmviexperiment.repository

import com.nsystem.androidmviexperiment.model.Champion
import kotlinx.coroutines.flow.Flow

interface ChampionEntityData {

    suspend fun search(query: CharSequence): Flow<Champion>

    suspend fun suggestions(query: CharSequence): Flow<List<String>>
}