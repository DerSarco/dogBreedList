package com.sarco.dogbreed.viewmodels

import androidx.lifecycle.ViewModel
import com.sarco.dogbreed.utils.getFiltersFavs

/******
Project dogBreed made with love by carlosmunoz
at 14-06-22 07:47

com.sarco.dogbreed.viewmodels
nobody cares about rights reserved.
 ******/
class BreedDataFavoritesViewModel: ViewModel() {
    private val selectedItems = mutableListOf<String>()
    val selectedItemsInfo: MutableList<String>
        get() = selectedItems

    private val selectedBooleans = arrayListOf<Boolean>()
    val selectedBooleansInfo: List<Boolean>
        get() = selectedBooleans


    fun getSelectedFilters(filtersForDialog: Array<String>): BooleanArray {
        selectedBooleans.clear()
        filtersForDialog.forEach { filter ->
            if (selectedItems.contains(filter.replaceFirstChar { it.lowercase() })) {
                selectedBooleans.add(true)
            } else {
                selectedBooleans.add(false)
            }
        }
        return selectedBooleans.toBooleanArray()
    }

    fun getFiltersForDialog(): Array<String> {
        return getFiltersFavs().toTypedArray()
    }

    fun checkFilterIfExist(filter: String): Boolean{
        return selectedItems.contains(filter.lowercase())
    }

    fun addFilters(filter: String) {
        selectedItems.add(filter.lowercase())
    }

    fun removeFilters(filter: String) {
        selectedItems.remove(filter.lowercase())
    }


}