package edu.farmingdale.bcs421_termproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        // Makes the splash screen a full screen activity
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Wait 2 seconds before switching from Splash Screen to Login Activity
        Handler().postDelayed({
            val intent = Intent(this, RegistrationActivity2::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}