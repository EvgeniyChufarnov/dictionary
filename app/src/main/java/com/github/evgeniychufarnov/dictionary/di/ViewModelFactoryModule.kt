package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import com.github.evgeniychufarnov.dictionary.ui.search.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideSearchViewModelFactory(dictionaryRepo: DictionaryRepo) =
        SearchViewModelFactory(dictionaryRepo)
}