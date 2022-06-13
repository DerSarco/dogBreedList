package com.sarco.dogbreed.repository

import android.util.Log
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.service.BreedListService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:30

com.sarco.dogbreed.repository
nobody cares about rights reserved.
 ******/
class BreedListRepository(private val service: BreedListService) {
    private val TAG = "BreedListRepository"

    suspend fun getBreedList(): Flow<List<String>> = service.fetchBreedList().map {
        it.getOrThrow()
    }.catch {
        emit(emptyList())
        Log.e(TAG, NullPointerException("Network error").toString())
    }

    suspend fun getBreedImages(breedName: String): Flow<List<BreedData>> =
        service.fetchBreedImages(breedName).map {
            val list = mapToBreedData(it.getOrThrow(), breedName)
            list
        }

    private fun mapToBreedData(orThrow: List<String>, breedName: String): List<BreedData> {
        val breedDataList = arrayListOf<BreedData>()
        for (i in orThrow) {
            val breed = BreedData(
                i,
                breedName,
                false
            )
            breedDataList.add(breed)
        }
        return breedDataList
    }
}