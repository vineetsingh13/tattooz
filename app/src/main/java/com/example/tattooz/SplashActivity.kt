package com.example.tattooz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.tattooz.databinding.ActivitySplashBinding
import com.example.tattooz.onboarding.OnboardActivity
import com.example.tattooz.registration.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({

            if(onBoardingFinished() && SigninFinished()){
                val intent= Intent(this, MainActivity::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }else if(onBoardingFinished()){
                val intent= Intent(this, LoginActivity::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }else{
                val intent= Intent(this, OnboardActivity::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }, 2000)
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    private fun SigninFinished(): Boolean{
        val sharedPref = getSharedPreferences("signin", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finish", false)
    }
}