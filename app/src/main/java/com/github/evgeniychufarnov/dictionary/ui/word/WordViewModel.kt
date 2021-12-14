package com.github.evgeniychufarnov.dictionary.ui.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.evgeniychufarnov.model.entities.WordEntity

class WordViewModel(
    wordEntity: WordEntity
) : ViewModel() {
    val word: LiveData<WordEntity> = MutableLiveData(wordEntity)
}