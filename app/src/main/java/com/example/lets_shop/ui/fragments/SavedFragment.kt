package com.example.lets_shop.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lets_shop.R
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

    // fetching the same mainViewModel instance from the activity.
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedBinding.inflate(layoutInflater)

        watchlistItem = ArrayList()
        watchList = ArrayList()
        // calling the read data method to store the shared prefrence data inside the watchlist
        readData()

        // observing the data and from liveData and checking which titles is actually matching with the titles stored in shared prefrence
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
        // setting up the reyclerView
        binding.favProductListRecyclerView.setHasFixedSize(true)
        binding.favProductListRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.favProductListRecyclerView.adapter = ProductListAdapter(requireContext(),watchlistItem,getString(R.string.saved_frag_type))



        return binding.root
    }

    // simple process to reading the data from the sharedprefrences.
    private fun readData() {
        val sharedPreferences =
            requireContext().getSharedPreferences(getString(R.string.sharedprefs_key), Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(getString(R.string.sharedprefs_key), ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }

}