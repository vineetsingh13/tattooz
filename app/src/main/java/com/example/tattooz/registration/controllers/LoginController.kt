package com.example.tattooz.registration.controllers

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth

class LoginController {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }
}