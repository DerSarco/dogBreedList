package com.sarco.dogbreed.ui.fragments.breedList

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sarco.dogbreed.R
import com.sarco.dogbreed.data.api.BreedNetwork
import com.sarco.dogbreed.databinding.FragmentDogBreedListBinding
import com.sarco.dogbreed.repository.BreedListRepository
import com.sarco.dogbreed.service.BreedListService
import com.sarco.dogbreed.ui.fragments.bredDetailList.BreedDetailListFragment
import com.sarco.dogbreed.viewmodels.BreedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BreedListFragment : Fragment() {

    private var TAG: String = "DogBreedListFragment"

    private lateinit var binding: FragmentDogBreedListBinding
    private lateinit var adapter: BreedListAdapter
    private lateinit var viewModel: BreedViewModel


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
        getBreedList()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.recyclerViewDogBreed.visibility = if (!it) View.VISIBLE else View.GONE
        }

        viewModel.dogBreedListInfo.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setNewData(it)
            } else {
                Toast.makeText(context, "Data not retrieved", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun getBreedList() = CoroutineScope(Dispatchers.IO).launch {
        viewModel.getBreedList()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            BreedViewModel.DogBreedViewModelFactory(repository)
        )[BreedViewModel::class.java]
    }

    private fun setupAdapter() {
        //todo: agregar los perritos

        adapter = BreedListAdapter() { dogBreedName ->
            val bundle = bundleOf(BreedDetailListFragment.BREED_KEY to dogBreedName)
            view?.findNavController()
                ?.navigate(R.id.action_dogBreedListFragment_to_breedDetailListFragment, bundle)
        };
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorites -> {
                findNavController().navigate(R.id.action_dogBreedListFragment_to_breedDataFavoritesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupUI() {
        binding.recyclerViewDogBreed.apply {
            adapter = this@BreedListFragment.adapter
            layoutManager = GridLayoutManager(context, 2)
        }

        setHasOptionsMenu(true)
    }
}