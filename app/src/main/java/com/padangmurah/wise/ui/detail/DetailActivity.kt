package com.padangmurah.wise.ui.detail

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.padangmurah.wise.R
import com.padangmurah.wise.databinding.ActivityDetailBinding
import com.padangmurah.wise.ui.camera.CameraFragment

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    //    private var _viewModel: CameraViewModel? = null
    //    private val viewModel get() = _viewModel!!
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        currentImageUri = intent.getStringExtra(CameraFragment.EXTRA_CAMERAX_IMAGE)?.toUri()

        if (currentImageUri != null) {
            binding.ivImage.setImageURI(currentImageUri)
        } else {
            Toast.makeText(this, getString(R.string.image_not_found), Toast.LENGTH_SHORT).show()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }


        binding.ivImage.setImageURI(currentImageUri)

    }
}