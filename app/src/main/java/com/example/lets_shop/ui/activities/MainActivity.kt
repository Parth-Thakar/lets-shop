package com.example.lets_shop.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lets_shop.R
import com.example.lets_shop.ShopApplication
import com.example.lets_shop.databinding.ActivityMainBinding
import com.example.lets_shop.viewmodels.MainViewModel
import com.example.lets_shop.viewmodels.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Field Dependency Injection for every inject annotation
        (application as ShopApplication).applicationComponent.inject(this)
        //setting up the navigation hosted fragment in the fragment space created in layout file
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentSpace)

        val navController = navHostFragment!!.findNavController()

        val popupmenu = PopupMenu(this, null)
        popupmenu.inflate(R.menu.bottom_nav_menu)
        binding.bottomBar.setupWithNavController(popupmenu.menu, navController)

        //creating the instance of the mainViewModel to test the livedata using observables.
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)


        mainViewModel.productsLiveData.observe(this){
            Log.d("checkData",it.toString())
        }


    }
}