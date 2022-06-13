package com.sarco.dogbreed.data.entities

import com.google.gson.annotations.SerializedName

data class BreedListResponse<T>(
    @SerializedName("message")
    val message: T ,
    @SerializedName("status")
    val status: String,
)