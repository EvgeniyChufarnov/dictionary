package com.github.evgeniychufarnov.dictionary.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import com.github.evgeniychufarnov.dictionary.domain.ScreenState
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import kotlinx.coroutines.launch

class SearchViewModel(
    private val dictionaryRepo: DictionaryRepo
) : ViewModel() {
    private val _screenState = MutableLiveData<ScreenState<List<WordEntity>>>()
    val screenState: LiveData<ScreenState<List<WordEntity>>> = _screenState

    private val _wordClickedEvent = MutableLiveData<WordEntity?>()
    val wordClickedEvent: LiveData<WordEntity?> = _wordClickedEvent

    fun onSearchClicked(word: String) {
        viewModelScope.launch {
            _screenState.value = dictionaryRepo.search(word)
        }
    }

    fun onWordClicked(word: WordEntity) {
        _wordClickedEvent.value = word
    }

    fun onWordClickedEventFinished() {
        _wordClickedEvent.value = null
    }
}