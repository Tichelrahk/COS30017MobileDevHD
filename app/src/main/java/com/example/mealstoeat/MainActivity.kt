package com.example.mealstoeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cloudinary.android.MediaManager
import com.cloudinary.android.ResponsiveUrl
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import java.time.Clock
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fab = findViewById<View>(R.id.fab_add)
        val createIntent = Intent(this, DetailActivity::class.java)
        fab.setOnClickListener(){
            startActivity(createIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        val mealDB = MealRepository(this)
        val mealList = findViewById<RecyclerView>(R.id.mealList)

        val time = Clock.system(ZoneId.of("Australia/Melbourne")).millis()

//        val meal1 = Meal("Spaghetti Bolognese", "School/COS30017MobileDev/danijela-prijovic-qits91IZv1o-unsplash_jdu17c", time, 7, "")
//        val meal2 = Meal("Lemon Tart", "School/COS30017MobileDev/konstantinas-ladauskas-nLFfes9_OS8-unsplash_ubbyhc", time, 10, "")
//        val meal3 = Meal("Chicken Wings", "School/COS30017MobileDev/chad-montano-gE28aTnlqJA-unsplash_lynoq6", time, 6, "")
//        val meal4 = Meal("Mexican Fiesta", "School/COS30017MobileDev/ryan-concepcion-50KffXbjIOg-unsplash_wzeowg", time, 10, "")
        //var meals = mutableListOf<Meal>()
        //meals.add(meal1)

//        mealDB.deleteAll()
//        mealDB.insertMeal(meal1)
//        mealDB.insertMeal(meal2)
//        mealDB.insertMeal(meal3)
//        mealDB.insertMeal(meal4)

        var meals = mealDB.loadAllMeals()

        mealList.adapter = MealRowAdapter(meals)
        mealList.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}