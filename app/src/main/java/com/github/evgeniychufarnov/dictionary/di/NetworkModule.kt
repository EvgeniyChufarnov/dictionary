package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.repository.getDictionaryApi
import org.koin.dsl.module

val networkModule = module {
    single {
        getDictionaryApi()
    }
}