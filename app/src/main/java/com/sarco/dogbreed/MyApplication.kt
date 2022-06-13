package com.sarco.dogbreed

import android.app.Application
import com.sarco.dogbreed.utils.UserFavs

/******
Project dogBreed made with love by carlosmunoz
at 13-06-22 14:09

com.sarco.dogbreed
nobody cares about rights reserved.
 ******/

val userFavs: UserFavs by lazy {
    MyApplication.preferences!!
}

class MyApplication : Application() {
    companion object {
        var preferences: UserFavs? = null
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = UserFavs(applicationContext)
    }
}
