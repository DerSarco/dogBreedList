package com.sarco.dogbreed.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sarco.dogbreed.repository.BreedListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 21:25

com.sarco.dogbreed.viewmodels
nobody cares about rights reserved.
 ******/
class BreedViewModel(private val repository: BreedListRepository) : ViewModel() {

    private val TAG = "DogBreedViewModel"

    private var loading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean>
        get() = loading

    private val dogBreedList = MutableLiveData<List<String>>()
    val dogBreedListInfo: MutableLiveData<List<String>>
        get() = dogBreedList

    suspend fun getBreedList() = viewModelScope.launch {
        loading.postValue(true)
        val dataResponse = repository.getBreedList().first()
        dogBreedList.postValue(dataResponse).also {
            loading.postValue(false)
        }
    }

    class DogBreedViewModelFactory(
        private val repository: BreedListRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BreedViewModel::class.java)) {
                return BreedViewModel(repository) as T
            } else {
                throw NullPointerException("ViewModel Class Unknown")
            }

        }
    }

}