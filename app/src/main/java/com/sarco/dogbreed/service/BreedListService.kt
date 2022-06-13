package com.sarco.dogbreed.service

import android.util.Log
import com.sarco.dogbreed.data.api.BreedListAPI
import com.sarco.dogbreed.data.entities.BreedListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:31

com.sarco.dogbreed.service
nobody cares about rights reserved.
 ******/
class BreedListService(private var breedListAPI: BreedListAPI) {
    private val TAG = "BreedListService"

    suspend fun fetchBreedList(): Flow<List<String>> {
        return flow {
            val result = breedListAPI.getAllBreeds().message.keys.toList()
            emit(result)
        }.catch {
            emit(emptyList())
        }
    }

}