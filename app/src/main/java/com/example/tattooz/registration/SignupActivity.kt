package com.example.tattooz.registration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.tattooz.MainActivity
import com.example.tattooz.R
import com.example.tattooz.databinding.ActivitySignupBinding
import com.example.tattooz.registration.controllers.SigninController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val signin=SigninController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginButton.setOnClickListener {
            val email = binding.EmailInputText.text.toString()
            val newPwd = binding.PwdInputText.text.toString()
            val pwd = binding.ConfirmPwdInputText.text.toString()

            if (pwd.length <= 6) {
                binding.ConfirmPwdInputText.error = "Minimum length is 7"
                binding.ConfirmPwdInputText.requestFocus()
                return@setOnClickListener
            } else if (newPwd.length <= 6) {
                binding.PwdInputText.error = "Minimum length is 7"
                binding.PwdInputText.requestFocus()
                return@setOnClickListener
            } else if (newPwd != pwd) {
                binding.PwdInputText.error = "Different password"
                binding.PwdInputText.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val success = signin.signUp(email, pwd)
                    if (success) {
                        navigateToMainActivity()
                        SigninFinished()
                    } else {
                        showToast("Sign up failed. Please try again.")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast("An error occurred. Please try again.")
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun SigninFinished(){
        val sharedPref = getSharedPreferences("signin", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finish", true)
        editor.apply()
    }
}