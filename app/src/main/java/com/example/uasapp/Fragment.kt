package com.example.uasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uasapp.fragment.fCrud
import com.example.uasapp.fragment.fHome
import com.example.uasapp.fragment.fProfile
import com.example.uasapp.fragment.fUser
import com.google.android.material.bottomnavigation.BottomNavigationView

class Fragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        fun getMenuItemId(fragmentName: String): Int {
            return when (fragmentName) {
                "fHome" -> R.id.bot_menu_home
                "fUser" -> R.id.bot_menu_user
                "fCrud" -> R.id.bot_menu_crud
                "fProfile" -> R.id.bot_menu_profile
                else -> R.id.bot_menu_home
            }
        }

        fun loadFragment(fragmentName: String){
            val newFragment = when (fragmentName) {
                "fHome" -> fHome()
                "fUser" -> fUser()
                "fCrud" -> fCrud()
                "fProfile" -> fProfile()
                else -> fHome()
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, newFragment)
            transaction.commit()

            bottomNav.menu.findItem(getMenuItemId(fragmentName))?.isChecked = true
        }

        val targetFragmentName = intent.getStringExtra("TARGET_FRAGMENT")
        if (targetFragmentName != null) {
            loadFragment(targetFragmentName)
        }

        bottomNav.setOnItemSelectedListener { item ->
            val fragmentName = when (item.itemId) {
                R.id.bot_menu_home -> "fHome"
                R.id.bot_menu_user -> "fUser"
                R.id.bot_menu_crud -> "fCrud"
                R.id.bot_menu_profile -> "fProfile"
                else -> "fHome"
            }

            loadFragment(fragmentName)

            true
        }
    }
}