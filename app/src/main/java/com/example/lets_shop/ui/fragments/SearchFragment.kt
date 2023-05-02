package com.example.lets_shop.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lets_shop.R
import com.example.lets_shop.adapters.ProductListAdapter
import com.example.lets_shop.databinding.FragmentSearchBinding
import com.example.lets_shop.models.Product
import com.example.lets_shop.viewmodels.MainViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    // fetching the same mainViewModel instance from the activity.
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var list: List<Product>
    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        list = listOf()
        // setting up the recyclerview
        binding.searchRecyclerView.setHasFixedSize(true)
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ProductListAdapter(requireContext(), list, getString(R.string.search_frag_type))
        binding.searchRecyclerView.adapter = adapter

        // setting up the recyclerview with all products fetched from the viewmodel
        mainViewModel.productsLiveData.observe(viewLifecycleOwner) {
            list = it
            binding.spinKitView.visibility = View.GONE
            adapter.updateDataList(list)
        }

        // calling the searchProduct() method to get the string from the editext and search in existing list.
        searchProduct()

        return binding.root
    }

    private lateinit var searchText: String

    private fun searchProduct() {
        // adding the textChangedlistner to search the product contain the same title keywords present in the edit text
        binding.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                searchText = s.toString().toLowerCase()
                updateRecyclerView()
            }
        })
    }

    // Updating the recyclerview accoriding to current updated search watchlist.
    private fun updateRecyclerView() {
        val data = ArrayList<Product>()
        for (item in list) {
            var coinName = item.title.toLowerCase()
            if (coinName.contains(searchText)) {
                data.add(item)
            }
        }
        adapter.updateDataList(data)
    }

    // clearing the edit text whenever the searchfragment goes into background
    override fun onPause() {
        super.onPause()
        binding.searchEditText.text.clear()
    }

}