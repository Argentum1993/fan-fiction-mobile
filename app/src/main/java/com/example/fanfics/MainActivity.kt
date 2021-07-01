package com.example.fanfics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fanfics.ui.auth.LoginActivity
import com.example.fanfics.ui.home.HomeViewModel
import com.example.fanfics.ui.user.profile.ProfileFragment
import com.example.fanfics.ui.user.profile.USER_OBJ
import com.example.fanfics.utils.SessionManager

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.clearAuthComponent()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.userFanficsFragment
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.hide()
        navView.setupWithNavController(navController)

        mainViewModel.logout.observe(this, {
            onLogout(null)
        })

        mainViewModel.profile.observe(this, {
            navController.navigate(R.id.action_navigation_home_to_profileFragment)
        })
    }

    fun onLogout(view: View?) {
        val sessionManager = SessionManager(this)
        sessionManager.logout()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}