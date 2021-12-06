package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.dictionary.data.CombinedDictionaryRepo
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import org.koin.dsl.module

val repoModule = module {

    single<DictionaryRepo> {
        CombinedDictionaryRepo(get(), get())
    }
}