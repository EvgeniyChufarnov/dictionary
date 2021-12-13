package com.github.evgeniychufarnov.model

import com.github.evgeniychufarnov.model.entities.WordEntity

interface DictionaryRepo {
    suspend fun search(word: String): ScreenState<List<WordEntity>>
    suspend fun getHistory(): List<String>
    suspend fun searchLocal(word: String): WordEntity?
}