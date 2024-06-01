package com.example.tattooz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.tattooz.databinding.ActivitySplashBinding
import com.example.tattooz.onboarding.OnboardActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent(this, OnboardActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }, 2000)
    }
}