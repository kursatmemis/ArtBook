package com.kursatmemis.artbook.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import com.kursatmemis.artbook.R
import com.kursatmemis.artbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

    }

    private fun setupToolbar() {
        binding.toolbar.inflateMenu(R.menu.main_act_menu)
        binding.toolbar.title = "Art Book"
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_add_art -> {
                    val navController = findNavController(R.id.fragmentContainerView)
                    if (navController.currentDestination?.id != R.id.detailFragment) {
                        navController.navigate(R.id.action_feedFragment_to_detailFragment)
                    }
                    true
                }

                else -> {false}
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_act_menu, menu)
        return true
    }
}