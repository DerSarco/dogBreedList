package com.sarco.dogbreed.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sarco.dogbreed.repository.BreedListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:25

com.sarco.dogbreed.viewmodels
nobody cares about rights reserved.
 ******/
class DogBreedViewModel(private val repository: BreedListRepository) : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()

    private val dogBreedList = MutableLiveData<List<String>>()
    val dogBreedListInfo: MutableLiveData<List<String>>
        get() = dogBreedList

    fun getBreedList() = viewModelScope.launch(Dispatchers.IO) {
        isLoading.postValue(true)
        dogBreedList.postValue(repository.getBreedList().first())
    }

    class DogBreedViewModelFactory(
        private val repository: BreedListRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DogBreedViewModel::class.java)) {
                return DogBreedViewModel(repository) as T
            } else {
                throw NullPointerException("ViewModel Class Unknown")
            }

        }
    }

}