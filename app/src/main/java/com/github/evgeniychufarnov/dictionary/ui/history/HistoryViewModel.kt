package com.github.evgeniychufarnov.dictionary.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.evgeniychufarnov.model.DictionaryRepo
import com.github.evgeniychufarnov.model.ScreenState
import com.github.evgeniychufarnov.model.entities.WordEntity
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val dictionaryRepo: DictionaryRepo
) : ViewModel() {
    private val _history = MutableLiveData<List<String>>()
    val history: LiveData<List<String>> = _history

    private val _wordClickedEvent = MutableLiveData<WordEntity?>()
    val wordClickedEvent: LiveData<WordEntity?> = _wordClickedEvent

    init {
        viewModelScope.launch {
            _history.value = dictionaryRepo.getHistory()
        }
    }

    fun onWordClicked(word: String) {
        viewModelScope.launch {
            val result = dictionaryRepo.searchLocal(word)

            result?.let {
                _wordClickedEvent.value = it
            }
        }
    }

    fun onWordClickedEventFinished() {
        _wordClickedEvent.value = null
    }
}