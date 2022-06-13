package com.sarco.dogbreed.ui.fragments.dogBreedList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sarco.dogbreed.databinding.DogBreedRowItemBinding
import com.sarco.dogbreed.databinding.FragmentDogBreedListBinding

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 20:19

com.sarco.dogbreed.ui.fragments.dogBreedList
nobody cares about rights reserved.
 ******/
class DogBreedListAdapter(val list: List<Int> = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),private val listener: (String) -> Unit) :
    RecyclerView.Adapter<DogBreedListAdapter.ViewHolder>() {

    private lateinit var binding: DogBreedRowItemBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = DogBreedRowItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind("Perro $position", listener)
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(binding: DogBreedRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tvText = binding.tvBreedTitle
        private val rowCardItem = binding.rowCardItem

        fun bind(text: String, listener: (String) -> Unit) {
            tvText.text = text
            rowCardItem.setOnClickListener {
               listener(tvText.text.toString())
            }

        }

    }
}