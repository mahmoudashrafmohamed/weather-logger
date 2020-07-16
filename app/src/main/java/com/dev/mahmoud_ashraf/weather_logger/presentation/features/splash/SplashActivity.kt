package com.dev.mahmoud_ashraf.weather_logger.presentation.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.mahmoud_ashraf.weather_logger.R
import com.dev.mahmoud_ashraf.weather_logger.presentation.features.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // open home activity
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}