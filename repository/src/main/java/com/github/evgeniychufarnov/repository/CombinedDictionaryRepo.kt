package com.github.evgeniychufarnov.repository

import com.github.evgeniychufarnov.model.DictionaryRepo
import com.github.evgeniychufarnov.model.ScreenState
import com.github.evgeniychufarnov.model.entities.MeaningEntity
import com.github.evgeniychufarnov.model.entities.WordEntity
import com.github.evgeniychufarnov.repository.local.DictionaryDao
import com.github.evgeniychufarnov.repository.local.entities.LocalWorldEntity
import com.github.evgeniychufarnov.repository.remote.DictionaryApi
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

    override suspend fun getHistory(): List<String> = withContext(dispatcher) {
        dictionaryDao.getAllWords().map {
            it.key
        }.distinct()
    }

    override suspend fun searchLocal(word: String): WordEntity? = withContext(dispatcher) {
        dictionaryDao.getWord(word)?.toWordEntity()
    }
}

private fun LocalWorldEntity.toWordEntity(): WordEntity {
    return WordEntity(
        id,
        word,
        listOf(
            MeaningEntity(
                meaningId,
                imageUrl,
                transcription
            )
        )
    )
}

private fun List<LocalWorldEntity>.toWordEntities(): List<WordEntity> {
    return map { it.toWordEntity() }
}

private fun List<WordEntity>.toLocalWordEntities(keyWord: String): List<LocalWorldEntity> {
    return map {
        val meaning = it.meanings.firstOrNull() ?: MeaningEntity(
            0,
            "",
            ""
        )

        LocalWorldEntity(
            id = it.id,
            key = keyWord,
            word = it.word,
            searchDate = System.currentTimeMillis(),
            meaningId = meaning.id,
            imageUrl = meaning.imageUrl,
            transcription = meaning.transcription
        )
    }
}