package com.padangmurah.wise.ui.detail

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.facebook.shimmer.ShimmerFrameLayout
import com.padangmurah.wise.R
import com.padangmurah.wise.databinding.ActivityDetailBinding
import com.padangmurah.wise.ui.camera.CameraFragment
import com.padangmurah.wise.ui.common.factory.ViewModelFactory
import com.padangmurah.wise.util.HtmlConverter
import com.padangmurah.wise.util.Result
import com.padangmurah.wise.util.UriConverter
import com.padangmurah.wise.util.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private var _viewModel: DetailViewModel? = null
    private val viewModel get() = _viewModel!!
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
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        _viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        currentImageUri = intent.getStringExtra(CameraFragment.EXTRA_CAMERAX_IMAGE)?.toUri()
        currentImageUri?.let { uri ->
            viewModel.setUri(uri)
        }
        if (currentImageUri != null) {
            binding.ivImage.setImageURI(currentImageUri)
        } else {
            Toast.makeText(this, getString(R.string.image_not_found), Toast.LENGTH_SHORT).show()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            try {
                val converter = UriConverter()
                val imageFile =
                    converter.uriToFile(currentImageUri!!, this@DetailActivity).reduceFileImage()

                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "image",
                    imageFile.name,
                    requestImageFile
                )
                viewModel.predict(multipartBody)
            } catch (e: Exception) {
                Toast.makeText(this@DetailActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.result.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showShimmer(true)
                    }

                    is Result.Success -> {
                        val converter = HtmlConverter()
                        val treatment = converter.convertToHtml(result.data.data?.details?.treatment ?: "")
                        binding.tvClassName.text = result.data.data?.details?.className
                        binding.tvPrediction.text = result.data.data?.prediction
                        binding.tvTreatment.setText(treatment, lifecycleScope)
                        binding.tvTreatment.updateTextSize(16f)
                        showShimmer(false)
                    }

                    is Result.Error -> {
                        showShimmer(false)
                        binding.cvImage.visibility = View.GONE
                        AlertDialog.Builder(this)
                            .setTitle("Something went wrong")
                            .setMessage(result.error)
                            .setPositiveButton("Try Again") { dialog, _ ->
                                dialog.dismiss()
                                finish()
                            }
                            .show()
                    }

                }
            }
        }

        binding.ivImage.setImageURI(currentImageUri)


    }

    private fun showShimmer(show: Boolean) {
        if (show) {
            binding.sflImage.startShimmer()
            binding.sflClassName.startShimmer()
            binding.sflPrediction.startShimmer()
            binding.sflTreatmentLine1.startShimmer()
            binding.sflTreatmentLine2.startShimmer()
            binding.sflTreatmentLine3.startShimmer()
            binding.sflTreatmentLine4.startShimmer()
            binding.sflTreatmentLine5.startShimmer()
            binding.btnRecord.visibility = View.GONE
        } else {
            binding.sflImage.stopShimmer()
            binding.sflClassName.stopShimmer()
            binding.sflPrediction.stopShimmer()
            binding.sflTreatmentLine1.stopShimmer()
            binding.sflTreatmentLine2.stopShimmer()
            binding.sflTreatmentLine3.stopShimmer()
            binding.sflTreatmentLine4.stopShimmer()
            binding.sflTreatmentLine5.stopShimmer()
            binding.sflImage.visibility = View.GONE
            binding.sflClassName.visibility = View.GONE
            binding.sflPrediction.visibility = View.GONE
            binding.sflTreatmentLine1.visibility = View.GONE
            binding.sflTreatmentLine2.visibility = View.GONE
            binding.sflTreatmentLine3.visibility = View.GONE
            binding.sflTreatmentLine4.visibility = View.GONE
            binding.sflTreatmentLine5.visibility = View.GONE
            binding.btnRecord.visibility = View.VISIBLE
        }
    }
}