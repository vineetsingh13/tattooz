package com.example.tattooz.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tattooz.MainActivity
import com.example.tattooz.R
import com.example.tattooz.databinding.ActivityLoginBinding
import com.example.tattooz.registration.controllers.LoginController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}