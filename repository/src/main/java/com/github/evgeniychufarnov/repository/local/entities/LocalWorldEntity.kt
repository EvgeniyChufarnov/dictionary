package com.github.evgeniychufarnov.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class LocalWorldEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "key_word") val key: String,
    @ColumnInfo(name = "search_date") val searchDate: Long,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "meaning_id") val meaningId: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "transcription") val transcription: String
)