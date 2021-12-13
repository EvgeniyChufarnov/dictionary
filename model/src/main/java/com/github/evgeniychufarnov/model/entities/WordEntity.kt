package com.github.evgeniychufarnov.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("text") val word: String,
    @SerializedName("meanings") val meanings: List<MeaningEntity>
) : Parcelable