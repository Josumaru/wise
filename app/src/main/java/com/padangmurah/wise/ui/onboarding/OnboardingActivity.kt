package com.padangmurah.wise.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.lifecycle.ViewModelProvider
import com.padangmurah.wise.MainActivity
import com.padangmurah.wise.R
import com.padangmurah.wise.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private var _binding: ActivityOnboardingBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: OnboardingViewModel? = null
    private val viewModel get() = _viewModel!!

    private val currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityOnboardingBinding.inflate(layoutInflater)
        _viewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.page.observe(this) { page ->
            when (page) {
                1 -> {
                    binding.tvOnboardingCardTitle.text = "Penanganan Segera"
                    binding.tvOnboardingCardSubtitle.text = "Wise menyediakan layanan untuk memberikan penanganan selanjutnya pada luka kulit anda"
                }
                2 -> {
                    val dpToPx = (20 * binding.root.context.resources.displayMetrics.density).toInt()

                    val firstLayoutParams = binding.cvOnboardingBulletFirst.layoutParams as ViewGroup.MarginLayoutParams
                    firstLayoutParams.marginStart = dpToPx
                    binding.cvOnboardingBulletFirst.layoutParams = firstLayoutParams

                    val secondLayoutParams = binding.cvOnboardingBulletSecond.layoutParams as ViewGroup.MarginLayoutParams
                    secondLayoutParams.marginEnd = dpToPx
                    binding.cvOnboardingBulletSecond.layoutParams = secondLayoutParams

                    binding.btnOnboarding.text = "Get Started"
                    binding.tvOnboardingCardTitle.text = "Akses Dimana Saja"
                    binding.tvOnboardingCardSubtitle.text = "Gunakan Wise dimanapun anda berada dan dapatkan lokasi Rumah Sakit Terdekat"
                    binding.ivOnboarding.setImageResource(R.drawable.img_onboarding_second)
                    binding.btnOnboarding.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        binding.btnOnboarding.setOnClickListener {
            viewModel.setPage(2)
        }
    }
}