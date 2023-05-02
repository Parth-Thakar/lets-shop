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
    val item: ProductDetailFragmentArgs by navArgs()
    var itemCount = 1

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(layoutInflater)

        val data = item.productargs!!



        Glide.with(this).load(data.image).thumbnail(
            Glide.with(this).load(R.drawable.spinner)
        ).into(binding.ivItem)

        binding.catTextView.text = data.category

        binding.tvItemPrice.text = data.price.toString()
        binding.tvItemDetail.text = data.description
        binding.tvItemName.text = data.title


        binding.btnMinus.setOnClickListener {
            itemCount--
            binding.tvItemQuantity.text = itemCount.toString()
        }

        binding.btnPlus.setOnClickListener {
            itemCount++
            binding.tvItemQuantity.text = itemCount.toString()
        }

        binding.btnShare.setOnClickListener {
            val drawable: BitmapDrawable = binding.ivItem.drawable as BitmapDrawable
            val bitmap: Bitmap = drawable.bitmap

            val bitmapPath: String = MediaStore.Images.Media.insertImage(
                activity?.applicationContext?.contentResolver,
                bitmap,
                "title",
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
            startActivity(Intent.createChooser(intent, "Share"))


        }


        addToFav(data)

        return binding.root
    }
    var watchList: ArrayList<String>? = null
    var isCheck = false

    private fun addToFav(data: Product) {
        readData()
        isCheck = if (watchList!!.contains(data.title)) {
            binding.btnFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
            true
        } else {
            binding.btnFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
            false
        }

        binding.btnFav.setOnClickListener {
            isCheck = if (!isCheck) {
                if (!watchList!!.contains(data.title)) {
                    watchList!!.add(data.title)
                }
                storeData()
                binding.btnFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                true
            } else {
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

    private fun storeData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val editor = sharedPreferences.edit()
        val json = gson.toJson(watchList)
        editor.putString("watchlist", json)
        editor.apply()
        Log.e("added", watchList.toString())
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