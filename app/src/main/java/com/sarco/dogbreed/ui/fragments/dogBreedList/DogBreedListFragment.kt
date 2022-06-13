package com.sarco.dogbreed.ui.fragments.dogBreedList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sarco.dogbreed.R
import com.sarco.dogbreed.databinding.FragmentDogBreedListBinding


class DogBreedListFragment : Fragment() {

    private lateinit var binding: FragmentDogBreedListBinding
    private lateinit var adapter: DogBreedListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogBreedListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        setupAdapter()
        setupUI()

        return binding.root
    }

    private fun setupAdapter() {
        //todo: agregar los perritos

        adapter = DogBreedListAdapter() { dogBreedName ->
            Toast.makeText(context, dogBreedName, Toast.LENGTH_SHORT).show()
        };
    }


    private fun setupUI() {
        binding.recyclerViewDogBreed.apply {
            adapter = this@DogBreedListFragment.adapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}