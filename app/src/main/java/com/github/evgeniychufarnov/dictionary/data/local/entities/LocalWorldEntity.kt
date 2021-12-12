package com.github.evgeniychufarnov.dictionary.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.evgeniychufarnov.dictionary.domain.entities.MeaningEntity

@Entity(tableName = "words")
data class LocalWorldEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "key_word") val key: String,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "meaning_id") val meaningId: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "transcription") val transcription: String
)