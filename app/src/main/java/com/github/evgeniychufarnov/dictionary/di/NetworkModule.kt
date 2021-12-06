package com.github.evgeniychufarnov.dictionary.di

import com.github.evgeniychufarnov.dictionary.data.remote.DictionaryApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi = Retrofit.Builder()
        .baseUrl("https://dictionary.skyeng.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DictionaryApi::class.java)

}