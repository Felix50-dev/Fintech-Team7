package com.aad.fintech.ui.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aad.fintech.databinding.ActivityHomeBinding
import com.aad.fintech.databinding.ActivityLoginBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}