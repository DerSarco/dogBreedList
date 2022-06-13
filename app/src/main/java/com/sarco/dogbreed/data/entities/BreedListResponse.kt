package com.sarco.dogbreed.data.entities

import com.google.gson.annotations.SerializedName

/*
* If you don't know how to handle different key names in a API response, use generics.
* */
data class BreedListResponse<T>(
    @SerializedName("message")
    val message: T ,
    @SerializedName("status")
    val status: String,
)