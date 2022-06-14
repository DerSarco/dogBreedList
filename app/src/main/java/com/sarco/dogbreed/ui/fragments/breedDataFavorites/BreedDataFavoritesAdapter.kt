package com.sarco.dogbreed.ui.fragments.breedDataFavorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sarco.dogbreed.R
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.databinding.BreedFavoriteItemRowBinding

/******
Project dogBreed made with love by carlosmunoz
at 13-06-22 14:45

com.sarco.dogbreed.ui.fragments.breedDataFavorites
nobody cares about rights reserved.
 ******/
class BreedDataFavoritesAdapter(private val listener: (BreedData) -> Unit) :
    RecyclerView.Adapter<BreedDataFavoritesAdapter.ViewHolder>() {

    private var list: List<BreedData> = listOf()
    private lateinit var binding: BreedFavoriteItemRowBinding
    private lateinit var context: Context

    fun setNewData(newList: List<BreedData>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = BreedFavoriteItemRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(list[position], listener)
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(binding: BreedFavoriteItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tvText = binding.tvBreedName
        private val ivBreedImage = binding.ivBreedImage
        private val ivIsFavorite = binding.ivIsFavorite

        fun bind(breed: BreedData, listener: (BreedData) -> Unit) {
            tvText.text = breed.dogName.replaceFirstChar { it.uppercase() }
            ivBreedImage.load(breed.imageUrl)
            if(breed.isFavorite){
                ivIsFavorite.setImageResource(R.drawable.ic_favorite)
            }else{
                ivIsFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
            ivIsFavorite.setOnClickListener {
                listener(breed)
            }

        }

    }
}