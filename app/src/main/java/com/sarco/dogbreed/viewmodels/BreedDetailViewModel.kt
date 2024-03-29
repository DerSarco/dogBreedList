package com.sarco.dogbreed.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.repository.BreedListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/******
Project dogBreed made with love by carlosmunoz
at 13-06-22 08:59

com.sarco.dogbreed.viewmodels
nobody cares about rights reserved.
 ******/
class BreedDetailViewModel(
    private val repository: BreedListRepository
) : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean>
        get() = loading

    private val breedImageList = MutableLiveData<List<BreedData>>()
    val breedImageListInfo: MutableLiveData<List<BreedData>>
        get() = breedImageList

    fun getBreedImages(breedName: String) = viewModelScope.launch {
        loading.postValue(true)
        withContext(Dispatchers.IO){
            val breedDetailListResult = repository.getBreedImages(breedName.lowercase()).firstOrNull().also {
                loading.postValue(false)
            }
            if(!breedDetailListResult.isNullOrEmpty()){
                breedImageList.postValue(breedDetailListResult)
            }
        }
    }


    class DogBreedDetailFactory(private val repository: BreedListRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BreedDetailViewModel::class.java)) {
                return BreedDetailViewModel(repository) as T
            } else {
                throw NullPointerException("ViewModel Class Unknown")
            }
        }
    }
}

