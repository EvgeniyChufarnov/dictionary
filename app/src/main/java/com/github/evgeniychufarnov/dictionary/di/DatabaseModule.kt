package com.github.evgeniychufarnov.dictionary.di

import android.content.Context
import androidx.room.Room
import com.github.evgeniychufarnov.dictionary.data.local.DictionaryDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDictionaryDao() = Room.databaseBuilder(
        context,
        DictionaryDatabase::class.java,
        "database.db"
    )
        .build()
        .dictionaryDao()
}