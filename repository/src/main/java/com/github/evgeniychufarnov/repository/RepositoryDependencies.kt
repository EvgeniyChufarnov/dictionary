package com.github.evgeniychufarnov.repository

import android.content.Context
import androidx.room.Room
import com.github.evgeniychufarnov.repository.local.DictionaryDatabase
import com.github.evgeniychufarnov.repository.remote.DictionaryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getDictionaryDao(context: Context) = Room.databaseBuilder(
    context,
    DictionaryDatabase::class.java,
    "database.db"
)
    .fallbackToDestructiveMigration()
    .build()
    .dictionaryDao()

fun getDictionaryApi() = Retrofit.Builder()
    .baseUrl("https://dictionary.skyeng.ru/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(DictionaryApi::class.java)