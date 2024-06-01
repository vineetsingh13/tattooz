package com.example.tattooz.registration.controllers

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class SigninController {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signUp(email: String, pwd: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pwd).await()
            result.user != null
        } catch (e: Exception) {
            false
        }
    }
}