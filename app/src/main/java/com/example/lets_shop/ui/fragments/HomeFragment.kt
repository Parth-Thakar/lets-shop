package com.example.lets_shop.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lets_shop.R
import com.example.lets_shop.adapters.ProductListAdapter
import com.example.lets_shop.databinding.FragmentHomeBinding
import com.example.lets_shop.viewmodels.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         //inflating the layout
        binding = FragmentHomeBinding.inflate(layoutInflater)



        binding.productListRecyclerView.setHasFixedSize(true)
        binding.productListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        mainViewModel.productsLiveData.observe(viewLifecycleOwner) {

            binding.productListRecyclerView.adapter =
                ProductListAdapter(requireContext(), it, "home")
            binding.spinKitViewHome.visibility = View.GONE

        }

        return binding.root
    }


}