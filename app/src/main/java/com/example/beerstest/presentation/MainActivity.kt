package com.example.beerstest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.beerstest.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var keepSplashOnScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSplashScreen()
        setContentView(R.layout.activity_main)
    }

    private fun setupSplashScreen() {
        // Set splash screen
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }

        // Create some artificial delay to simulate some local database fetch for example
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            keepSplashOnScreen = false
        }
    }
}

private const val SPLASH_DELAY = 800L
