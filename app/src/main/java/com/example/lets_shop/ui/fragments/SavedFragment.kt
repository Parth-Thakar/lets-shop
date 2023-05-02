package com.example.lets_shop.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lets_shop.adapters.ProductListAdapter
import com.example.lets_shop.databinding.FragmentSavedBinding
import com.example.lets_shop.models.Product
import com.example.lets_shop.viewmodels.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SavedFragment : Fragment() {
    private lateinit var binding : FragmentSavedBinding
    private lateinit var watchList : ArrayList<String>
    private lateinit var watchlistItem : ArrayList<Product>
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedBinding.inflate(layoutInflater)

        watchlistItem = ArrayList()
        watchList = ArrayList()
        readData()


        mainViewModel.productsLiveData.observe(viewLifecycleOwner) {
            for(watchData in watchList){
                for(item in it){
                    if(watchData == item.title)
                    {
                        watchlistItem.add(item)
                    }
                }
            }
        }

        binding.favProductListRecyclerView.setHasFixedSize(true)
        binding.favProductListRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.favProductListRecyclerView.adapter = ProductListAdapter(requireContext(),watchlistItem,"saved")



        return binding.root
    }

    private fun readData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("watchlist", ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }

}