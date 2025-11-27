package com.example.appscheduler.ui.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appscheduler.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity :  AppCompatActivity() {
    private lateinit var viewBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        navigateToMain()
    }
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        try {
            finishAffinity()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}