package com.example.dinedash.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgument
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dinedash.R
import com.example.dinedash.databinding.ActivityMainBinding
import com.example.dinedash.db.DineDashDatabase
import com.example.dinedash.models.User
import com.example.dinedash.repository.DineDashRepository
import com.example.dinedash.viewmodel.DineDashViewModel
import com.example.dinedash.viewmodel.DineDashViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: DineDashViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
        val bundle = intent.extras
        val user = bundle?.getParcelable<User>("user")
        val destination = navController.graph.findNode(R.id.profileFragment)
        destination?.addArgument("user", NavArgument.Builder().setDefaultValue(user).build())

        navController.addOnDestinationChangedListener{_, destination, _ ->
            if (destination.id == R.id.detailsFragment || destination.id == R.id.cartFragment
                || destination.id == R.id.checkoutFragment) {
                // Hide the bottom navigation bar
                binding.bottomNavView.visibility = View.GONE
            } else {
                // Show the bottom navigation bar for other fragments
                binding.bottomNavView.visibility = View.VISIBLE
            }
        }
//        For viewModel implementation
        val dineDashRepository = DineDashRepository(DineDashDatabase.getDatabase(this))
        val dashProvider = DineDashViewModelFactory(dineDashRepository)
        viewModel = ViewModelProvider(this, dashProvider)[DineDashViewModel::class.java]



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(R.id.profileFragment)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }


}