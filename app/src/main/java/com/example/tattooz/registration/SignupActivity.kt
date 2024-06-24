package com.example.tattooz.registration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.tattooz.LoadingDialog
import com.example.tattooz.MainActivity
import com.example.tattooz.R
import com.example.tattooz.databinding.ActivitySignupBinding
import com.example.tattooz.registration.controllers.SigninController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val signin=SigninController()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    var name=""

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginButton.setOnClickListener {
            val email = binding.EmailInputText.text.toString()
            val newPwd = binding.PwdInputText.text.toString()
            val pwd = binding.ConfirmPwdInputText.text.toString()

            val loading=LoadingDialog(this)


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

            if(email.isNotEmpty() && newPwd.isNotEmpty() && pwd.isNotEmpty()){
                loading.startLoading()
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val success = signin.signUp(email, pwd)
                        if (success) {

                            name=binding.nameInputText.text.toString()
                            SigninFinished(name)

                            runOnUiThread {
                                loading.isDismiss()
                                navigateToMainActivity()
                            }
                        } else {
                            runOnUiThread {
                                loading.isDismiss()
                                showToast("An error occurred. Please try again.")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast("An error occurred. Please try again.")
                    }
                }
            }
        }


        auth = FirebaseAuth.getInstance()

        if (this.auth.currentUser != null) {

        }


        //        configuring google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleButton.setOnClickListener {
            signIn()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun SigninFinished(str: String){
        val sharedPref = getSharedPreferences("signin", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finish", true)
        editor.putString("NAME",str)
        editor.apply()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {

                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    SigninFinished(account.displayName.toString())
                    firebaseAuthWithGoogle(account.idToken!!)
                    navigateToMainActivity()
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                }
            } else {
                println("signInActivity ${exception.toString()}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                } else {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}