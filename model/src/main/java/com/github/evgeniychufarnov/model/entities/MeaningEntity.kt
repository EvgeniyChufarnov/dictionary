package com.github.evgeniychufarnov.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MeaningEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("transcription") val transcription: String
) : Parcelable {
    fun getFullImageUrl() = "https:$imageUrl"
}