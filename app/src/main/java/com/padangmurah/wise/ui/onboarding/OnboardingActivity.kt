package com.padangmurah.wise.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.padangmurah.wise.MainActivity
import com.padangmurah.wise.R
import com.padangmurah.wise.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGetStarted.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_get_started -> {
                    val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}