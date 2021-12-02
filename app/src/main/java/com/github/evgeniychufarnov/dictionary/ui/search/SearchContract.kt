package com.github.evgeniychufarnov.dictionary.ui.search

import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity

interface SearchContract {
    enum class ScreenState {
        SUCCESS, LOADING, ERROR, NOTHING_TO_SHOW
    }

    interface View {
        fun showWords(words: List<WordEntity>)
        fun changeState(state: ScreenState)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onSearchClicked(word: String)
    }
}