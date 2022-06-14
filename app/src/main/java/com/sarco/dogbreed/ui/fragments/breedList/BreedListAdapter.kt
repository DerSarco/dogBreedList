package com.sarco.dogbreed.ui.fragments.breedList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarco.dogbreed.databinding.DogBreedRowItemBinding

/******
Project dogBreed made with love by carlosmunoz
at 12-06-22 20:19

com.sarco.dogbreed.ui.fragments.dogBreedList
nobody cares about rights reserved.
 ******/
class BreedListAdapter(private val listener: (String) -> Unit) :
    RecyclerView.Adapter<BreedListAdapter.ViewHolder>() {
    private var list: List<String> = listOf()
    private lateinit var binding: DogBreedRowItemBinding
    private lateinit var context: Context

    fun setNewData(newList: List<String>){
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = DogBreedRowItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(list[position], listener)
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