package com.sarco.dogbreed.utils

import com.google.gson.Gson
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.userFavs

/******
Project dogBreed made with love by carlosmunoz
at 13-06-22 17:38

com.sarco.dogbreed.utils
nobody cares about rights reserved.
 ******/

fun getUsersFavs(): MutableList<BreedData> {
    val userFavsSP = userFavs.favoritesBreeds
    return Gson().fromJson(userFavsSP, Array<BreedData>::class.java).toMutableList()
}

fun getFiltersFavs(): MutableSet<String> {
    val userFavsSP = userFavs.favoritesBreeds
    val json = Gson().fromJson(userFavsSP, Array<BreedData>::class.java).toMutableList()
    val exist = mutableSetOf<String>()

    json.forEach {
        if (!exist.contains(it.dogName))
            exist.add(it.dogName.replaceFirstChar { char -> char.uppercase() })
    }

    return exist
}

fun getFilteredList(filterList: MutableList<String>): MutableList<BreedData> {
    val filteredList = mutableListOf<BreedData>()

    for (filter in filterList){
       val filtered = getUsersFavs().filter {
            it.dogName == filter
        }
        filteredList.addAll(filtered)
    }
    return filteredList
}

fun addNewFav(breedData: BreedData) {
    val userFavsSP = userFavs.favoritesBreeds
    val jsonSerialized = Gson().fromJson(userFavsSP, Array<BreedData>::class.java).toMutableList()
    jsonSerialized.add(breedData)
    val toJson = Gson().toJson(jsonSerialized)
    userFavs.favoritesBreeds = toJson
}

fun deleteFav(breedData: BreedData): MutableList<BreedData> {
    val userFavsSP = userFavs.favoritesBreeds
    val jsonSerialized = Gson().fromJson(userFavsSP, Array<BreedData>::class.java).toMutableList()
    val finded = jsonSerialized.find { breedSP ->
        breedSP.imageUrl == breedData.imageUrl
    }
    jsonSerialized.removeAt(jsonSerialized.indexOf(finded))
    val toJson = Gson().toJson(jsonSerialized)
    userFavs.favoritesBreeds = toJson
    return jsonSerialized
}