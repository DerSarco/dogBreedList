package com.sarco.dogbreed.ui.fragments.breedDataFavorites

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sarco.dogbreed.R
import com.sarco.dogbreed.databinding.FragmentBreedDataFavoritesBinding
import com.sarco.dogbreed.utils.deleteFav
import com.sarco.dogbreed.utils.getFilteredList
import com.sarco.dogbreed.utils.getFiltersFavs
import com.sarco.dogbreed.utils.getUsersFavs
import com.sarco.dogbreed.viewmodels.BreedDataFavoritesViewModel

class BreedDataFavoritesFragment : Fragment() {

    private val TAG = "BreedDataFavoritesFragment"

    private lateinit var binding: FragmentBreedDataFavoritesBinding
    private lateinit var adapter: BreedDataFavoritesAdapter

    private val viewModel: BreedDataFavoritesViewModel by viewModels()

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
        if (viewModel.selectedItemsInfo.size != 0)
            adapter.setNewData(getFilteredList(viewModel.selectedItemsInfo))
        else
            adapter.setNewData(getUsersFavs())


        activity?.title = "Favorites"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupAdapter() {
        adapter = BreedDataFavoritesAdapter {
            with(adapter) {
                if (viewModel.selectedItemsInfo.size != 0) {
                    deleteFav(it)
                    setNewData(getFilteredList(viewModel.selectedItemsInfo))
                } else {
                    setNewData(deleteFav(it))
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            R.id.menu_filters -> {
                displayFiltersDialog()
                true
            }
            R.id.menu_clear -> {
                adapter.setNewData(getUsersFavs())
                Toast.makeText(context, "Filters cleaned!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun displayFiltersDialog() {
        val filtersForDialog = viewModel.getFiltersForDialog()
        val dialog = AlertDialog.Builder(context!!)
        val selectedBooleans = viewModel.getSelectedFilters(filtersForDialog)
        dialog.setTitle("Select Breed for filter").setMultiChoiceItems(
            filtersForDialog, selectedBooleans
        ) { _, which, isChecked ->
            if (isChecked) {
                // If the user checked the item, add it to the selected items
                viewModel.addFilters(filtersForDialog[which])
            } else if (viewModel.checkFilterIfExist(filtersForDialog[which])) {
                // Else, if the item is already in the array, remove it
                viewModel.removeFilters(filtersForDialog[which])
            }
        }.setPositiveButton(
            "Accept"
        ) { _, _ ->
            if (viewModel.selectedItemsInfo.size != 0) {
                adapter.setNewData(getFilteredList(viewModel.selectedItemsInfo))
            } else {
                adapter.setNewData(getUsersFavs())
            }
        }
            .setNegativeButton(
                "Close"
            ) { _, _ ->

            }

        dialog.create().show()
    }


    override fun onDestroy() {
        activity?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
        super.onDestroy()
    }


}