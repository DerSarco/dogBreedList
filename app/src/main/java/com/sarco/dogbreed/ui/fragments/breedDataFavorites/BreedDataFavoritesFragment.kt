package com.sarco.dogbreed.ui.fragments.breedDataFavorites

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sarco.dogbreed.R
import com.sarco.dogbreed.databinding.FragmentBreedDataFavoritesBinding
import com.sarco.dogbreed.utils.deleteFav
import com.sarco.dogbreed.utils.getFilteredList
import com.sarco.dogbreed.utils.getFiltersFavs
import com.sarco.dogbreed.utils.getUsersFavs

class BreedDataFavoritesFragment : Fragment() {

    private val TAG = "BreedDataFavoritesFragment"

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
                if (selectedItems.size != 0) {
                    deleteFav(it)
                    setNewData(getFilteredList(selectedItems))
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

    private val selectedItems = mutableListOf<String>()
    private fun displayFiltersDialog() {
        val filtersForDialog = getFiltersForDialog()
        val dialog = AlertDialog.Builder(context!!)

        val selectedBooleans = getSelectedFilters(filtersForDialog)

        dialog.setTitle("Select Breed for filter").setMultiChoiceItems(
            filtersForDialog, selectedBooleans.toBooleanArray()
        ) { _, which, isChecked ->
            if (isChecked) {
                // If the user checked the item, add it to the selected items
                selectedItems.add(filtersForDialog[which].lowercase())
            } else if (selectedItems.contains(filtersForDialog[which].lowercase())) {
                // Else, if the item is already in the array, remove it
                selectedItems.remove(filtersForDialog[which].lowercase())
            }
        }.setPositiveButton(
            "Accept"
        ) { _, _ ->
            Log.d(TAG, selectedItems.toString())
            if (selectedItems.size != 0) {
                adapter.setNewData(getFilteredList(selectedItems))
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

    private fun getSelectedFilters(filtersForDialog: Array<String>): ArrayList<Boolean> {
        val selectedBooleans = arrayListOf<Boolean>()
        filtersForDialog.forEach { filter ->
            if (selectedItems.contains(filter.replaceFirstChar { it.lowercase() })) {
                selectedBooleans.add(true)
            } else {
                selectedBooleans.add(false)
            }
        }
        return selectedBooleans
    }

    private fun getFiltersForDialog(): Array<String> {
        return getFiltersFavs().toTypedArray()
    }


    override fun onDestroy() {
        activity?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
        super.onDestroy()
    }


}