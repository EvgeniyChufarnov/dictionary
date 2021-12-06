package com.github.evgeniychufarnov.dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.evgeniychufarnov.dictionary.data.local.entities.LocalWorldEntity

@Dao
interface DictionaryDao {
    @Insert
    fun cacheWords(words: List<LocalWorldEntity>)

    @Query("SELECT * FROM words WHERE key_word = :key")
    fun getWords(key: String): List<LocalWorldEntity>
}