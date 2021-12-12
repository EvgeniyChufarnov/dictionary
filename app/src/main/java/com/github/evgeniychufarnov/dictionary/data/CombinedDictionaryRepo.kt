package com.github.evgeniychufarnov.dictionary.data

import com.github.evgeniychufarnov.dictionary.data.local.DictionaryDao
import com.github.evgeniychufarnov.dictionary.data.local.entities.LocalWorldEntity
import com.github.evgeniychufarnov.dictionary.data.remote.DictionaryApi
import com.github.evgeniychufarnov.dictionary.domain.DictionaryRepo
import com.github.evgeniychufarnov.dictionary.domain.ScreenState
import com.github.evgeniychufarnov.dictionary.domain.entities.MeaningEntity
import com.github.evgeniychufarnov.dictionary.domain.entities.WordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CombinedDictionaryRepo(
    private val dictionaryApi: DictionaryApi,
    private val dictionaryDao: DictionaryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DictionaryRepo {

    override suspend fun search(word: String): ScreenState<List<WordEntity>> =
        withContext(dispatcher) {

            val cache = dictionaryDao.getWords(word)

            if (cache.isNotEmpty()) {
                ScreenState.Success(cache.toWordEntities())
            } else {
                getWordsFromNetwork(word)
            }
        }

    private suspend fun getWordsFromNetwork(word: String): ScreenState<List<WordEntity>> {
        return try {
            val response = dictionaryApi.search(word)
            dictionaryDao.cacheWords(response.toLocalWordEntities(word))
            ScreenState.Success(response)
        } catch (e: Exception) {
            ScreenState.Error
        }
    }
}

private fun List<LocalWorldEntity>.toWordEntities(): List<WordEntity> {
    return map {
        WordEntity(
            it.id,
            it.word,
            listOf(MeaningEntity(it.meaningId, it.imageUrl, it.transcription))
        )
    }
}

private fun List<WordEntity>.toLocalWordEntities(keyWord: String): List<LocalWorldEntity> {
    return map {
        val meaning = it.meanings.firstOrNull() ?: MeaningEntity(0, "", "")

        LocalWorldEntity(
            id = it.id,
            key = keyWord,
            word = it.word,
            meaningId = meaning.id,
            imageUrl = meaning.imageUrl,
            transcription = meaning.transcription
        )
    }
}