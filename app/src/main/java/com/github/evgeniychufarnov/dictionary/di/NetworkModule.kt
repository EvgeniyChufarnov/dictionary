package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.dictionary.data.remote.DictionaryApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}