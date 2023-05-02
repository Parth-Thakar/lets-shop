package com.example.lets_shop.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lets_shop.R
import com.example.lets_shop.databinding.ProductListItemBinding
import com.example.lets_shop.models.Product
import com.example.lets_shop.ui.fragments.HomeFragmentDirections
import com.example.lets_shop.ui.fragments.SavedFragmentDirections
import com.example.lets_shop.ui.fragments.SearchFragmentDirections

// Adapter class for the recyclerview used in the application.
class ProductListAdapter(val context: Context, var list: List<Product>, val fragmentType: String) :
    RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val item = list[position]

        //setting up the title and the price of recyclerview product
        holder.binding.productTitleTextView.text = item.title
        holder.binding.productPriceTextView.text = item.price.toString() + "$"

        //setting up the imageView of recyclerview product item
        Glide.with(context)
            .load(item.image)
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.productImageView)

        // logic to check which fragment is calling the ProductDetailFragment so that it can navigate using the direction
        // class of that fragment generated in build.
        holder.itemView.setOnClickListener {
            if (fragmentType == context.getString(R.string.home_frag_type)) {
                findNavController(it).navigate(
                    HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(item)
                )
            }
            if (fragmentType == context.getString(R.string.search_frag_type)) {
                findNavController(it).navigate(
                    SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(item)
                )
            }
            if (fragmentType == context.getString(R.string.saved_frag_type)) {
                findNavController(it).navigate(
                    SavedFragmentDirections.actionSavedFragmentToProductDetailFragment(item)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateDataList(dataItem: List<Product>) {
        list = dataItem
        notifyDataSetChanged()
    }

    inner class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ProductListItemBinding.bind(view)
    }


}