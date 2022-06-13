package com.sarco.dogbreed.data.api

import com.sarco.dogbreed.data.entities.BreedListResponse
import retrofit2.Call
import retrofit2.http.GET

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:22

com.sarco.dogbreed.data.api
nobody cares about rights reserved.
 ******/
interface BreedListAPI {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedListResponse<Map<String, List<String>>>

}