package com.github.evgeniychufarnov.dictionary.data

import com.github.evgeniychufarnov.dictionary.data.local.DictionaryDao
import com.github.evgeniychufarnov.dictionary.data.local.entities.LocalWorldEntity
import com.github.evgeniychufarnov.dictionary.data.remote.DictionaryApi
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import io.reactivex.Single

class CombinedDictionaryRepo(
    private val dictionaryApi: DictionaryApi,
    private val dictionaryDao: DictionaryDao
) : DictionaryRepo {
    override fun search(word: String): Single<List<WordEntity>> {
        return dictionaryDao.getWords(word)
            .flatMap {
                if (it.isEmpty()) {
                    dictionaryApi.search(word)
                        .doOnSuccess { response ->
                            dictionaryDao.cacheWords(response.toLocalWordEntities(word))
                        }
                } else {
                    Single.just(it.toWordEntities())
                }
            }
    }
}

private fun List<LocalWorldEntity>.toWordEntities(): List<WordEntity> {
     return map {
         WordEntity(it.word)
     }
}

private fun List<WordEntity>.toLocalWordEntities(keyWord: String): List<LocalWorldEntity> {
    return map {
        LocalWorldEntity(
            key = keyWord,
            word = it.word
        )
    }
}