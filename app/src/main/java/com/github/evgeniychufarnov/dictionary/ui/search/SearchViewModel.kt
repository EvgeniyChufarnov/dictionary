package com.github.evgeniychufarnov.dictionary.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

sealed class ScreenState<out T> {
    class Success<T>(val value: T) : ScreenState<T>()
    object Loading : ScreenState<Nothing>()
    object Error : ScreenState<Nothing>()
}

class SearchViewModel(
    private val dictionaryRepo: DictionaryRepo
) : ViewModel() {
    private val _screenState = MutableLiveData<ScreenState<List<WordEntity>>>()
    val screenState: LiveData<ScreenState<List<WordEntity>>> = _screenState

    private var disposable: Disposable? = null
        set(value) {
            field?.takeIf { !it.isDisposed }?.dispose()
            field = value
        }

    fun onSearchClicked(word: String) {
        disposable = dictionaryRepo.search(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _screenState.value = ScreenState.Loading
            }
            .subscribe({ newWords ->
                _screenState.value = ScreenState.Success(newWords)
            }, {
                _screenState.value = ScreenState.Error
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable = null
    }
}