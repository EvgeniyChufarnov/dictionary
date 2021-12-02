package com.github.evgeniychufarnov.dictionary.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class LocalWorldEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "key_word") val key: String,
    @ColumnInfo(name = "word") val word: String,
)