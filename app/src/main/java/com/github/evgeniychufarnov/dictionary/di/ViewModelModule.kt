package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.dictionary.ui.history.HistoryFragment
import com.github.evgeniychufarnov.dictionary.ui.history.HistoryViewModel
import com.github.evgeniychufarnov.dictionary.ui.search.SearchViewModel
import com.github.evgeniychufarnov.dictionary.ui.word.WordViewModel
import com.github.evgeniychufarnov.model.entities.WordEntity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    scope<HistoryFragment> {
        viewModel { HistoryViewModel(get()) }
    }

    viewModel { SearchViewModel(get()) }
    viewModel { (word: WordEntity) -> WordViewModel(word) }
}