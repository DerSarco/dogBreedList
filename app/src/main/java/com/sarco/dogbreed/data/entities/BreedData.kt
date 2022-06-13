package com.sarco.dogbreed.data.entities

import com.google.gson.annotations.SerializedName

/******
Project dogBreed made with love by carlosmunoz
at 13-06-22 01:23

com.sarco.dogbreed.data.entities
nobody cares about rights reserved.
 ******/
data class BreedData(
    val imageUrl: String,
    val dogName: String = "",
    var isFavorite: Boolean = false
)

