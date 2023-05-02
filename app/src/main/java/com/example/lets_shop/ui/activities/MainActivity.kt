package com.example.lets_shop.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lets_shop.R
import com.example.lets_shop.ShopApplication
import com.example.lets_shop.databinding.ActivityMainBinding
import com.example.lets_shop.utils.NetworkUtils
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


        // If the network is not available suggesting the user to turned on the internet to fetch the fresh data, using alert dialog box.

        if (!NetworkUtils.isInternetAvailable(this)) {
            // Create the object of AlertDialog Builder class
            val builder = AlertDialog.Builder(this)
            // Set the message show for the Alert time
            builder.setMessage("Please Turn on your Internet to load fresh products")
            // Set Alert Title
            builder.setTitle("Your Internet is OFF !")
            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(true)
            // Create the Alert dialog
            val alertDialog = builder.create()
            // Show the Alert Dialog box
            alertDialog.show()
        }


    }
}