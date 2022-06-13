package com.sarco.dogbreed.ui.fragments.breedDataFavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.databinding.FragmentBreedDataFavoritesBinding
import com.sarco.dogbreed.userFavs

class BreedDataFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentBreedDataFavoritesBinding

    private lateinit var adapter: BreedDataFavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDataFavoritesBinding.inflate(inflater, container, false)
        setupAdapter()
        setupUI()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupUI() {
        binding.recyclerViewDogBreedFavorites.apply {
            adapter = this@BreedDataFavoritesFragment.adapter
            layoutManager = GridLayoutManager(context, 2)
        }
        val userFavsSP = userFavs.favoritesBreeds
        val jsonSerialized =
            Gson().fromJson(userFavsSP, Array<BreedData>::class.java).toMutableList()
        adapter.setNewData(jsonSerialized)
    }

    private fun setupAdapter() {
        adapter = BreedDataFavoritesAdapter {
            deleteBreedFavorite(it)
        }
    }

    private fun deleteBreedFavorite(it: BreedData) {
        val userFavsSP = userFavs.favoritesBreeds
        val jsonSerialized =
            Gson().fromJson(userFavsSP, Array<BreedData>::class.java).toMutableList()
        val finded = jsonSerialized.find { breedSP ->
            breedSP.imageUrl == it.imageUrl
        }
        jsonSerialized.removeAt(jsonSerialized.indexOf(finded))
        adapter.setNewData(jsonSerialized.toList())
        val toJson = Gson().toJson(jsonSerialized)
        userFavs.favoritesBreeds = toJson
    }


}