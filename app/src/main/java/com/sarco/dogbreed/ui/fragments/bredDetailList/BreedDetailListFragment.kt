package com.sarco.dogbreed.ui.fragments.bredDetailList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.sarco.dogbreed.R
import com.sarco.dogbreed.data.api.BreedNetwork
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.databinding.FragmentBreedDetailListBinding
import com.sarco.dogbreed.repository.BreedListRepository
import com.sarco.dogbreed.service.BreedListService
import com.sarco.dogbreed.userFavs
import com.sarco.dogbreed.utils.addNewFav
import com.sarco.dogbreed.utils.deleteFav
import com.sarco.dogbreed.viewmodels.BreedDetailViewModel

class BreedDetailListFragment : Fragment() {

    companion object {
        val BREED_KEY = "breed"
    }

    private lateinit var binding: FragmentBreedDetailListBinding
    private lateinit var adapter: BreedDetailListAdapter

    private lateinit var viewModel: BreedDetailViewModel


    private val service = BreedListService(BreedNetwork.getRetrofitAllBreedList())
    private val repository = BreedListRepository(service)

    private var breed: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBreedDetailListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        breed = arguments?.getString(BREED_KEY)
        setupViewModel()
        setupAdapter()
        setupUI()
        setupObservers()
        return binding.root
    }

    private fun setupUI() {
        binding.recyclerViewDogBreedDetail.apply {
            adapter = this@BreedDetailListFragment.adapter
            layoutManager = GridLayoutManager(context, 2)
        }
        activity?.title = breed
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

    }

    private fun setupAdapter() {
        adapter = BreedDetailListAdapter {
            adapter.updateData(it)
            if (it.isFavorite) {
                addNewFav(it)
            } else {
                adapter.setNewData(deleteFav(it))
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            BreedDetailViewModel.DogBreedDetailFactory(repository)
        )[BreedDetailViewModel::class.java]

    }

    private fun setupObservers() {
        viewModel.getBreedImages(breed!!)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.recyclerViewDogBreedDetail.visibility = if (!it) View.VISIBLE else View.GONE
        }

        viewModel.breedImageListInfo.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setNewData(it)
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroy() {
        activity?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
        super.onDestroy()
    }
}