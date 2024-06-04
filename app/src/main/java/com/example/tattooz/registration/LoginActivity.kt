package com.example.tattooz.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tattooz.MainActivity
import com.example.tattooz.R
import com.example.tattooz.databinding.ActivityLoginBinding
import com.example.tattooz.registration.controllers.LoginController

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginController = LoginController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createProfile.setOnClickListener {
            navigateToSignUpScreen()
        }

        binding.LoginButton.setOnClickListener {
            val email = binding.EmailInputText.text.toString()
            val pwd = binding.PwdInputText.text.toString()
            if(email.isEmpty() || pwd.isEmpty()){
                binding.EmailInput.requestFocus()
                binding.PwdInput.requestFocus()
            }else{
                loginController.login(email, pwd) { success ->
                    if (success) {
                        navigateToMainActivity()
                    } else {
                        showToast("Login failed. Please try again.")
                    }
                }
            }
        }
    }

    private fun navigateToSignUpScreen() {
        val intent = Intent(this@LoginActivity, SignupActivity::class.java)
        startActivity(intent)
        
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}