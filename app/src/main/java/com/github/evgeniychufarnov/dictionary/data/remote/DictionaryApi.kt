package com.github.evgeniychufarnov.dictionary.data.remote

import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {
    @GET("api/public/v1/words/search")
    suspend fun search(
        @Query("search") word: String
    ): List<WordEntity>
}