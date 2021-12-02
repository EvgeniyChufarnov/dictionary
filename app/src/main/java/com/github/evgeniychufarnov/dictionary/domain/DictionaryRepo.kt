package com.github.evgeniychufarnov.dictionary.domain

import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import io.reactivex.Single

interface DictionaryRepo {
    fun search(word: String): Single<List<WordEntity>>
}