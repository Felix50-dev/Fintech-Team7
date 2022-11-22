package com.aad.fintech.ui.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aad.fintech.databinding.ActivityWelcomeBinding

private const val SIGN_IN: String = "SIGN_IN"
private const val SIGN_UP: String = "SIGN_UP"

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setEvents()
    }

    private fun setEvents() {
        binding.getStartedButton.setOnClickListener() {
            var signUpIntent = Intent(this, RegisterActivity::class.java)
            signUpIntent.putExtra("signMode", SIGN_UP)
            startActivity(signUpIntent)
        }
        binding.loginButton.setOnClickListener() {
            var signInIntent = Intent(this, LoginActivity::class.java)
            signInIntent.putExtra("signMode", SIGN_IN)
            startActivity(signInIntent)
        }
    }
}