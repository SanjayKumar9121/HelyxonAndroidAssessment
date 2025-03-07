package com.sanjay.helyxonandroidassessment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.sanjay.helyxonandroidassessment.Fragments.AllProductsFragment
import com.sanjay.helyxonandroidassessment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar as SupportActionBar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val allProductsFragment = AllProductsFragment()
        replaceFragment(allProductsFragment)
    }

    //Replace Fragment
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        }

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}