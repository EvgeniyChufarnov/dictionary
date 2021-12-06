package com.github.evgeniychufarnov.dictionary.di

import androidx.room.Room
import com.github.evgeniychufarnov.dictionary.data.local.DictionaryDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            DictionaryDatabase::class.java,
            "database.db"
        )
            .build()
            .dictionaryDao()
    }
}