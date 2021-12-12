package com.github.evgeniychufarnov.dictionary.domain

import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity

interface DictionaryRepo {
    suspend fun search(word: String): ScreenState<List<WordEntity>>
    suspend fun getHistory(): List<String>
    suspend fun searchLocal(word: String): WordEntity?
}