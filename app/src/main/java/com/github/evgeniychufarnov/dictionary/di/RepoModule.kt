package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.model.DictionaryRepo
import com.github.evgeniychufarnov.repository.CombinedDictionaryRepo
import org.koin.dsl.module

val repoModule = module {

    single<DictionaryRepo> {
        CombinedDictionaryRepo(get(), get())
    }
}