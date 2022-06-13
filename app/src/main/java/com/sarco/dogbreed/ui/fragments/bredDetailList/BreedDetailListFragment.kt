package com.sarco.dogbreed.ui.fragments.bredDetailList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sarco.dogbreed.R
import com.sarco.dogbreed.databinding.FragmentBreedDetailListBinding

class BreedDetailListFragment : Fragment() {

    private lateinit var binding: FragmentBreedDetailListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBreedDetailListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

}