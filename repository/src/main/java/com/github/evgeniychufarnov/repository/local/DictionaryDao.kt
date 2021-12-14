package com.github.evgeniychufarnov.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.evgeniychufarnov.repository.local.entities.LocalWorldEntity

@Dao
interface DictionaryDao {
    @Insert
    fun cacheWords(words: List<LocalWorldEntity>)

    @Query("SELECT * FROM words WHERE key_word = :key")
    fun getWords(key: String): List<LocalWorldEntity>

    @Query("SELECT * FROM words ORDER BY search_date DESC")
    fun getAllWords(): List<LocalWorldEntity>

    @Query("SELECT * FROM words WHERE word = :word LIMIT 1")
    fun getWord(word: String): LocalWorldEntity?
}