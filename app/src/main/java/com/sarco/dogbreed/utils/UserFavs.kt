package com.sarco.dogbreed.utils

import android.content.Context
import android.content.SharedPreferences

/******
Project dogBreed made with love by carlosmunoz
at 13-06-22 14:10

com.sarco.dogbreed.utils
nobody cares about rights reserved.
 ******/
class UserFavs(context: Context) {
    //weather api provides three measurements: standard, metric, imperial

    private val favorites = "favorites"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(favorites, Context.MODE_PRIVATE)

    var favoritesBreeds: String?
        get() = preferences.getString(favorites, "[]")
        set(value) = preferences.edit().putString(favorites, value).apply()
}