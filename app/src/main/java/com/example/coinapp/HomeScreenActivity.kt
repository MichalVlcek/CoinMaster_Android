package com.example.coinapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.coinapp.ui.homeScreen.HomeScreenFragment
import com.example.coinapp.utils.UserUtils

class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, HomeScreenFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logout()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        UserUtils.logoutUser(this)

        val intent = Intent(this, LoginActivity()::class.java)
        startActivity(intent)
    }
}