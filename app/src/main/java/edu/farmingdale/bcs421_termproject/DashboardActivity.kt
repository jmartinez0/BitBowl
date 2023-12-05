package edu.farmingdale.bcs421_termproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    lateinit var bottomNavigation : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        var homeFragment = HomeFragment()
        var foodFragment = FoodFragment()
        var accountFragment = AccountFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, homeFragment)
            commit()
        }

        // Change which fragment shows based on the selected item (home/add food/account)
        bottomNavigation.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout, homeFragment)
                        commit()
                    }
                    true
                }
                R.id.food -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout, foodFragment)
                        commit()
                    }
                    true
                }
                R.id.account -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout, accountFragment)
                        commit()
                    }
                    true
                }
                else -> { false}
            }
        }

    }
}