package com.sarco.dogbreed.repository

import android.accounts.NetworkErrorException
import android.util.Log
import com.google.gson.Gson
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.service.BreedListService
import com.sarco.dogbreed.userFavs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.nio.channels.NetworkChannel

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:30

com.sarco.dogbreed.repository
nobody cares about rights reserved.
 ******/
class BreedListRepository(private val service: BreedListService) {
    private val TAG = "BreedListRepository"

    suspend fun getBreedList(): Flow<List<String>> = service.fetchBreedList().map {
        it.getOrThrow().map { breed ->
            breed.replaceFirstChar { char ->
                char.uppercase()
            }
        }
    }.catch {
        emit(emptyList())
        Log.e(TAG, NullPointerException("Network error").toString())
    }

    suspend fun getBreedImages(breedName: String): Flow<List<BreedData>> =
        service.fetchBreedImages(breedName).map {
            val list = mapToBreedData(it.getOrThrow(), breedName)
            list
        }.catch {
            emit(listOf())
        }

    private fun mapToBreedData(orThrow: List<String>, breedName: String): List<BreedData> {
        val breedDataList = arrayListOf<BreedData>()
        val jsonFavs = Gson().fromJson(userFavs.favoritesBreeds, Array<BreedData>::class.java)

        for (i in orThrow) {
            val breed = BreedData(
                i,
                breedName,
                false
            )
            breedDataList.add(breed)
        }
        val filtered = jsonFavs.filter {
            it.dogName == breedName
        }

        filtered.forEach { favBreed ->
            val breedExist = breedDataList.find { breed ->
                breed.imageUrl == favBreed.imageUrl && breed.dogName == favBreed.dogName
            }
            breedExist?.isFavorite = true
        }

        return breedDataList
    }
}