package com.sarco.dogbreed.ui.fragments.dogBreedList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sarco.dogbreed.R
import com.sarco.dogbreed.data.api.BreedListAPI
import com.sarco.dogbreed.data.api.BreedNetwork
import com.sarco.dogbreed.databinding.FragmentDogBreedListBinding
import com.sarco.dogbreed.repository.BreedListRepository
import com.sarco.dogbreed.service.BreedListService
import com.sarco.dogbreed.viewmodels.DogBreedViewModel


class DogBreedListFragment : Fragment() {

    private var TAG: String = "DogBreedListFragment"

    private lateinit var binding: FragmentDogBreedListBinding
    private lateinit var adapter: DogBreedListAdapter
    private lateinit var viewModel: DogBreedViewModel


    private val service = BreedListService(BreedNetwork.getRetrofitAllBreedList())
    private val repository = BreedListRepository(service)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogBreedListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setupAdapter()
        setupViewModel()
        setupObservers()
        setupUI()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.getBreedList()

        viewModel.dogBreedListInfo.observe(viewLifecycleOwner){
           adapter.setNewData(it)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            DogBreedViewModel.DogBreedViewModelFactory(repository)
        )[DogBreedViewModel::class.java]
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