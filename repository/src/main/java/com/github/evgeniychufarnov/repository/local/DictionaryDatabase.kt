package com.github.evgeniychufarnov.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.evgeniychufarnov.repository.local.entities.LocalWorldEntity

@Database(entities = [LocalWorldEntity::class], version = 3, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}