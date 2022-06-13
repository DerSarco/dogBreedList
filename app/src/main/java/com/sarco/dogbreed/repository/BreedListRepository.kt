package com.sarco.dogbreed.repository

import android.util.Log
import com.sarco.dogbreed.service.BreedListService
import kotlinx.coroutines.flow.Flow
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

   suspend fun getBreedList(): Flow<List<String>>  = service.fetchBreedList()
}