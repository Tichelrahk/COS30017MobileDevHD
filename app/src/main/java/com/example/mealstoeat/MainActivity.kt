package com.example.mealstoeat

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

//    Drawer
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Drawe
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        Nav
        navView = findViewById(R.id.nav_view)
        val menuIntent = Intent(this, MainActivity::class.java)
        var order = ""

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.orderRatings -> {
                    order = "ratings"
                    menuIntent.putExtra("order", order)
                    startActivity(menuIntent)
                    true
                }
                R.id.orderName -> {
                    order = "name"
                    menuIntent.putExtra("order", order)
                    startActivity(menuIntent)
                    true
                }
                else -> {
                    order = "time"
                    menuIntent.putExtra("order", order)
                    startActivity(menuIntent)
                    true
                }
            }
        }

//        Fab
        val fab = findViewById<View>(R.id.fab_add)
        val createIntent = Intent(this, DetailActivity::class.java)
        fab.setOnClickListener(){
            startActivity(createIntent)
        }

        val nameValid = intent.getBooleanExtra("nameValid", false)
        val ratingValid = intent.getBooleanExtra("ratingValid", false)
        val imgValid = intent.getBooleanExtra("imgValid", false)
        val valid = intent.getBooleanExtra("valid", false)

//        check if data was received
        if (nameValid || ratingValid || imgValid || valid){
            val bottomSheetFragment = BottomSheetFragment(nameValid, ratingValid, imgValid, valid)
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }
    }

    override fun onResume() {
        super.onResume()
        val mealDB = MealRepository(this)
        val mealList = findViewById<RecyclerView>(R.id.mealList)

        val orderBy = this.intent.getStringExtra("order")

        var meals = mealDB.loadAllMealsByTime()

        orderBy.let {
            when (orderBy) {
                "name" -> meals = mealDB.loadAllMealsByName()
                "ratings" -> meals = mealDB.loadAllMealsByRating()
            }
        }

        mealList.adapter = MealRowAdapter(meals)
        mealList.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}