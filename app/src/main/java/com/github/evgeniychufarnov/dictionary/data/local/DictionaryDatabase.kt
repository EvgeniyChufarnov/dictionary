package com.github.evgeniychufarnov.dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.evgeniychufarnov.dictionary.data.local.entities.LocalWorldEntity

@Database(entities = [LocalWorldEntity::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}