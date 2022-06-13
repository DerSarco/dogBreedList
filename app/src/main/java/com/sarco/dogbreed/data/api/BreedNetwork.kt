package com.sarco.dogbreed.data.api

import com.sarco.dogbreed.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:12

com.sarco.dogbreed.data.api
nobody cares about rights reserved.
 ******/
object BreedNetwork {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    fun getRetrofitAllBreedList(): BreedListAPI = Retrofit.Builder()
        .baseUrl(Constants.API_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BreedListAPI::class.java)


}