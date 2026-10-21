package com.example.progr3ss

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.progr3ss.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var handlerThread: HandlerThread
    private lateinit var backgroundHandler: Handler

    companion object {
        private const val TAG = "SplashActivity"
        private const val SPLASH_TIME_OUT = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Splash screen started")

        // Smooth fade-in animation
        binding.splashContent.alpha = 0f
        binding.splashContent.animate()
            .alpha(1f)
            .setDuration(1000L)
            .start()

        // Create a background thread
        handlerThread = HandlerThread(
            "SplashHandlerThread",
            -10
        )
        handlerThread.start()

        // Create a handler on the background thread
        backgroundHandler = Handler(handlerThread.looper)

        // Navigate to MainActivity after 2 seconds
        backgroundHandler.postDelayed({
            runOnUiThread {
                if (!isFinishing && !isDestroyed) {
                    val intent = Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )

                    startActivity(intent)
                    finish()
                }
            }
        }, SPLASH_TIME_OUT)
    }

    override fun onDestroy() {
        if (::backgroundHandler.isInitialized) {
            backgroundHandler.removeCallbacksAndMessages(null)
        }

        if (::handlerThread.isInitialized) {
            handlerThread.quitSafely()
        }

        Log.d(TAG, "Splash screen destroyed")
        super.onDestroy()
    }
}