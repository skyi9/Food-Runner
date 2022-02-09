package com.learn.foodrunner.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.learn.foodrunner.R
import com.learn.foodrunner.fragment.FAQsFragment
import com.learn.foodrunner.fragment.FavouritesFragment
import com.learn.foodrunner.fragment.HomeFragment
import com.learn.foodrunner.fragment.ProfileFragment

class MainSpot : AppCompatActivity() {

    private lateinit var toolbar : androidx.appcompat.widget.Toolbar
    private lateinit var frameLayout : FrameLayout
    private lateinit var navigationView : NavigationView
    private lateinit var coordinatorLayout : CoordinatorLayout
    private lateinit var drawerLayout : DrawerLayout

    private var previousMenuItem : MenuItem? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_spot)

       /* txtData = findViewById(R.id.txtMainSpot)

        if (intent != null) {
            val details = intent.getBundleExtra("details")
            val data = details?.getString("data")

            if(data == "login"){
                txtData.text = "Mobile Number : ${details.getString("number")} \n" +
                        "Password : ${details.getString("password")}"
            }
            if (data == "register"){
                txtData.text = "Person Name : ${details.getString("name")} \n" +
                        "Email : ${details.getString("email")} \n " +
                        "Number : ${details.getString("phone")} \n "+
                        "Address : ${details.getString("address")} \n "+
                        "Password : ${details.getString("password")}"
            }
            if (data == "forgot"){
                txtData.text = "Number : ${details.getString("phone")} \n "+
                        "Email : ${details.getString("email")}"
            }
        }
        else {
            txtData.text = "No data received"
        }*/

         toolbar  = findViewById(R.id.toolbar)
         frameLayout  = findViewById(R.id.frame)
         coordinatorLayout  = findViewById(R.id.coordinatorLayout)
         navigationView  = findViewById(R.id.navigationView)
         drawerLayout = findViewById(R.id.drawerLayout)

        setUpToolbar()
        openHome()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this ,
        drawerLayout ,
            R.string.open_drawer, R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.home ->{
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouritesFragment())
                        .commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.faqs -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FAQsFragment())
                        .commit()
                    supportActionBar?.title = "FAQs"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    private fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openHome(){
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Home"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {
        when(supportFragmentManager.findFragmentById(R.id.frame)){
            !is HomeFragment -> openHome()

            else -> super.onBackPressed()
        }
    }

}

