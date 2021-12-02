package com.github.evgeniychufarnov.dictionary.ui.search

import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
    private val dictionaryRepo: DictionaryRepo
) : SearchContract.Presenter {
    private var view: SearchContract.View? = null

    private var disposable: Disposable? = null
        set(value) {
            field?.takeIf { !it.isDisposed }?.dispose()
            field = value
        }

    override fun attachView(view: SearchContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
        disposable = null
    }

    override fun onSearchClicked(word: String) {
        disposable = dictionaryRepo.search(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view?.changeState(SearchContract.ScreenState.LOADING)
            }
            .subscribe({ newWords ->
                view?.showWords(newWords)

                if (newWords.isNotEmpty()) {
                    view?.changeState(SearchContract.ScreenState.SUCCESS)
                } else {
                    view?.changeState(SearchContract.ScreenState.NOTHING_TO_SHOW)
                }
            }, {
                view?.changeState(SearchContract.ScreenState.ERROR)
            }
            )
    }
}