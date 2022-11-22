package com.aad.fintech.ui.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.navigation.fragment.NavHostFragment
import com.aad.fintech.R
import com.aad.fintech.databinding.ActivityRegisterBinding
import com.aad.fintech.ui.views.adapters.Sex
import com.aad.fintech.ui.views.adapters.SexAdapter

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.register_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.register_nav_graph, intent.extras)
    }

}