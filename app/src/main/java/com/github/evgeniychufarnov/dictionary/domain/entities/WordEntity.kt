package com.github.evgeniychufarnov.dictionary.domain.entities

import com.google.gson.annotations.SerializedName

data class WordEntity(
    @SerializedName("text") val word: String,
)