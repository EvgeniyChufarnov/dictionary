package com.github.evgeniychufarnov.dictionary

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.github.evgeniychufarnov.dictionary.data.CombinedDictionaryRepo
import com.github.evgeniychufarnov.dictionary.data.local.DictionaryDatabase
import com.github.evgeniychufarnov.dictionary.data.remote.DictionaryApi
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import com.github.evgeniychufarnov.dictionary.ui.search.SearchContract
import com.github.evgeniychufarnov.dictionary.ui.search.SearchPresenter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private val dictionaryApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    private val dictionaryDao by lazy {
        Room.databaseBuilder(
            this,
            DictionaryDatabase::class.java,
            "database.db"
        )
            .build()
            .dictionaryDao()
    }

    private val dictionaryRepo: DictionaryRepo by lazy {
        CombinedDictionaryRepo(dictionaryApi, dictionaryDao)
    }

    fun getSearchPresenter(): SearchContract.Presenter {
        return SearchPresenter(dictionaryRepo)
    }
}

val Fragment.app: App
    get() = requireActivity().application as App