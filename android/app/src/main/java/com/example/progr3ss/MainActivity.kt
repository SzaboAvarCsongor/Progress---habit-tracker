package com.example.progr3ss

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.progr3ss.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: MainActivity created.")

        // NavController a NavHostFragment-ből
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Top-level destinations (nem lesz náluk vissza nyíl)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.profileFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // BottomNavigationView összekötése
        binding.bottomNav.setupWithNavController(navController)

        // Bottom nav elrejtése login/register képernyőn
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment ->
                    binding.bottomNav.visibility = View.GONE
                else -> binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart: MainActivity") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume: MainActivity") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause: MainActivity") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop: MainActivity") }
    override fun onRestart() { super.onRestart(); Log.d(TAG, "onRestart: MainActivity") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy: MainActivity") }
}