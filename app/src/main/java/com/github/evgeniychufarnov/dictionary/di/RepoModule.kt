package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.dictionary.data.CombinedDictionaryRepo
import com.github.evgeniychufarnov.dictionary.data.local.DictionaryDao
import com.github.evgeniychufarnov.dictionary.data.remote.DictionaryApi
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideDictionaryRepo(
        dictionaryApi: DictionaryApi,
        dictionaryDao: DictionaryDao
    ): DictionaryRepo {
        return CombinedDictionaryRepo(dictionaryApi, dictionaryDao)
    }
}