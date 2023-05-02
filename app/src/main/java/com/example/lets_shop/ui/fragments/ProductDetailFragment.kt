package com.example.lets_shop.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.lets_shop.R
import com.example.lets_shop.databinding.FragmentProductDetailBinding
import com.example.lets_shop.models.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding

    // fetching the data passed in detail fragment by the lauchedFragment using navArgs() method in kotlin Delegation
    val item: ProductDetailFragmentArgs by navArgs()
    var itemCount = 1

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(layoutInflater)

        val data = item.productargs!!

        // Loading the data using glide and adding the Thumbnail util the picture is fully loaded
        Glide.with(this).load(data.image).thumbnail(
            Glide.with(this).load(R.drawable.spinner)
        ).into(binding.ivItem)

        // setting up the TextView
        binding.catTextView.text = data.category
        binding.tvItemPrice.text = "$ " + data.price.toString()
        binding.tvItemDetail.text = data.description
        binding.tvItemName.text = data.title

        // Dummy functioning of product add and remove quantity button
        binding.btnMinus.setOnClickListener {
            itemCount--
            binding.tvItemQuantity.text = itemCount.toString()
        }
        binding.btnPlus.setOnClickListener {
            itemCount++
            binding.tvItemQuantity.text = itemCount.toString()
        }

        // Functionality of Share Button in detail Fragment, Using Implicit Intent
        binding.btnShare.setOnClickListener {
            val drawable: BitmapDrawable = binding.ivItem.drawable as BitmapDrawable
            val bitmap: Bitmap = drawable.bitmap

            val bitmapPath: String = MediaStore.Images.Media.insertImage(
                activity?.applicationContext?.contentResolver,
                bitmap,
                getString(R.string.bitmap_image_title),
                null
            )

            val uri: Uri = Uri.parse(bitmapPath)

            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("image/png")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Product Name : ${data.title} \n Price : ${data.price} \n Category : ${data.category}"
            )
            startActivity(Intent.createChooser(intent, getString(R.string.share_text)))


        }
        // function to save the product inside the sharedPrefs
        addToFav(data)

        return binding.root
    }

    var watchList: ArrayList<String>? = null
    var isCheck = false

    // addToFav() method
    private fun addToFav(data: Product) {
        readData()
        // Cheking if the user has clicked the heart Icon in the detail fragment then chaging it to red and if did twice
        // then it to return back to normal
        isCheck = if (watchList!!.contains(data.title)) {
            binding.btnFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
            true
        } else {
            binding.btnFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
            false
        }
        // when heart icon is clicked
        binding.btnFav.setOnClickListener {
            isCheck = if (!isCheck) {
                if (!watchList!!.contains(data.title)) {
                    // checking if the the title is not existed in the watchlist then add the title in it.
                    watchList!!.add(data.title)
                }
                // calling the storedata function to add the current updated watchlist to shared prefs
                storeData()
                binding.btnFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                true
            } else {
                // if pressed twice removing the title from the watchlist and updated the list in shared prefrences.
                // and also setting back the color of heart icon back to normal
                binding.btnFav.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                watchList!!.remove(data.title)
                storeData()
                false
            }
        }
    }

    // simple process to creating and storing data inside the sharedprefrence.
    private fun storeData() {
        val sharedPreferences =
            requireContext().getSharedPreferences(getString(R.string.sharedprefs_key), Context.MODE_PRIVATE)
        val gson = Gson()
        val editor = sharedPreferences.edit()
        val json = gson.toJson(watchList)
        editor.putString(getString(R.string.sharedprefs_key), json)
        editor.apply()
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