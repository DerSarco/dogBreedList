package com.sarco.dogbreed.ui.fragments.bredDetailList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sarco.dogbreed.data.entities.BreedData
import com.sarco.dogbreed.databinding.BreedDetailItemRowBinding
import com.sarco.dogbreed.databinding.DogBreedRowItemBinding

/******
Project dogBreed made with love by carlosmunoz
at 13-06-22 10:01

com.sarco.dogbreed.ui.fragments.bredDetailList
nobody cares about rights reserved.
 ******/
class BreedDetailListAdapter(private val listener: (BreedData) -> Unit) :
    RecyclerView.Adapter<BreedDetailListAdapter.ViewHolder>() {
    private var list: List<BreedData> = listOf()
    private lateinit var binding: BreedDetailItemRowBinding
    private lateinit var context: Context

    fun setNewData(newList: List<BreedData>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = BreedDetailItemRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(list[position], listener)
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(binding: BreedDetailItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val ivBreedImage = binding.ivBreedImage
        private val tvText = binding.tvBreedTitle

        fun bind(breed: BreedData, listener: (BreedData) -> Unit) {
            ivBreedImage.load(breed.imageUrl)
        }

    }

}