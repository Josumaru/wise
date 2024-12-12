package com.padangmurah.wise.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.padangmurah.wise.R
import com.padangmurah.wise.databinding.FragmentCameraBinding
import com.padangmurah.wise.ui.common.factory.ViewModelFactory
import com.padangmurah.wise.ui.detail.DetailActivity
import com.padangmurah.wise.ui.setting.SettingFragment
import com.padangmurah.wise.util.createCustomTempFile
import com.padangmurah.wise.util.getImageUri
import com.padangmurah.wise.util.getLatestGalleryImage
import java.io.File

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var _viewModel: CameraViewModel? = null
    private val viewModel get() = _viewModel!!
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var cameraControl: CameraControl? = null
    private var isFlashOn = false
    private var currentImageUri: Uri? = null
    private var galleryImagePreview: Uri? = null
    private var imageCapture: ImageCapture? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
//        _viewModel = ViewModelProvider(this, factory)[CameraViewModel::class.java]
        startCamera()

        galleryImagePreview = getLatestGalleryImage(requireActivity())

        if (galleryImagePreview != null) {
            binding.ivGallery.setImageURI(galleryImagePreview)
        } else {
            Toast.makeText(requireActivity(), "No images found in gallery", Toast.LENGTH_SHORT)
                .show()
        }
        binding.cvFlash.setOnClickListener {
            cameraControl?.enableTorch(!isFlashOn)
            binding.ivFlash.setImageResource(if (isFlashOn) R.drawable.ic_flash_inactive else R.drawable.ic_flash)
            isFlashOn = !isFlashOn
        }

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.cvCapture.setOnClickListener {
            capturePhoto()
        }

        binding.cvGallery.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            analyzeImage(uri)
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.no_image_selected), Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = binding.pvCamera.surfaceProvider
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                cameraControl = camera.cameraControl
            } catch (exc: Exception) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.failed_to_start_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun capturePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(requireActivity())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    savePhotoToGallery(photoFile)
                    binding.ivGallery.setImageURI(photoFile.toUri())
                    analyzeImage(output.savedUri)
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_to_save_image_to_gallery),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun analyzeImage(imageUri: Uri?){
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(EXTRA_CAMERAX_IMAGE, imageUri.toString())
        startActivity(intent)
    }

    private fun savePhotoToGallery(photoFile: File) {
        val resolver = requireActivity().contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Wise")
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            resolver.openOutputStream(it).use { outputStream ->
                photoFile.inputStream().use { inputStream ->
                    inputStream.copyTo(outputStream!!)
                }
            }
        } ?: run {
            Toast.makeText(
                requireContext(),
                getString(R.string.failed_to_save_image_to_gallery), Toast.LENGTH_SHORT
            ).show()
        }
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.permission_request_granted),
                    Toast.LENGTH_LONG
                ).show()
                galleryImagePreview = getLatestGalleryImage(requireActivity())
                binding.ivGallery.setImageURI(galleryImagePreview)
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.permission_request_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        _binding = null
        _viewModel = null
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
    }
}