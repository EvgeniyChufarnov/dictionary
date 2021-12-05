package com.github.evgeniychufarnov.dictionary.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo

class SearchViewModelFactory(
    private val dictionaryRepo: DictionaryRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DictionaryRepo::class.java)
            .newInstance(dictionaryRepo)
    }
}