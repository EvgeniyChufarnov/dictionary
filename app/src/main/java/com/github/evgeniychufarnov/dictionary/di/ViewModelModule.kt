package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import com.github.evgeniychufarnov.dictionary.ui.search.SearchViewModel
import com.github.evgeniychufarnov.dictionary.ui.word.WordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { (word: WordEntity) -> WordViewModel(word) }
}