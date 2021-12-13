package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.repository.getDictionaryDao
import org.koin.dsl.module

val databaseModule = module {
    single {
        getDictionaryDao(get())
    }
}