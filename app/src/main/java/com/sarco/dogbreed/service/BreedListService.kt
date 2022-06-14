package com.sarco.dogbreed.service

import android.accounts.NetworkErrorException
import com.sarco.dogbreed.data.api.BreedListAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:31

com.sarco.dogbreed.service
nobody cares about rights reserved.
 ******/
class BreedListService(private var breedListAPI: BreedListAPI) {
    private val TAG = "BreedListService"

    suspend fun fetchBreedList(): Flow<Result<List<String>>> {
        return flow {
            val result = breedListAPI.getAllBreeds().message.keys.toList()
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(NetworkErrorException("Connection error")))
        }
    }

    suspend fun fetchBreedImages(breedName: String): Flow<Result<List<String>>> {
        return flow {
            val result = breedListAPI.getBreedPics(breedName).message.toList()
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(NetworkErrorException("Connection error")))
        }
    }

}