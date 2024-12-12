package com.padangmurah.wise.ui.hospital

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority
import com.padangmurah.wise.databinding.FragmentHospitalBinding
import com.padangmurah.wise.ui.common.factory.ViewModelFactory
import com.padangmurah.wise.util.Result

class HospitalFragment : Fragment() {

    private var _binding: FragmentHospitalBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: HospitalViewModel? = null
    private val viewModel get() = _viewModel!!

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    viewModel.setLatLng(lat, lon)
                    viewModel.getHospital(lat, lon, refresh = false)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setupViewModel()
        setupRecyclerView()
        setupSwipeRefresh()
        getMyLocationIfNeeded()
        loadInitialData()
        setupAction()
    }

    private fun setupAction() {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadInitialData() {
        if (viewModel.lat.value != null && viewModel.lon.value != null) {
            viewModel.getHospital(viewModel.lat.value!!, viewModel.lon.value!!, refresh = false)
        } else {
            checkLocationSettingsAndFetch()
        }
    }

    private fun checkLocationSettingsAndFetch() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        val settingsClient: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val task = settingsClient.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            getMyLocation()
        }.addOnFailureListener {
            viewModel.getHospital(null, null)
        }
    }


    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        _viewModel = ViewModelProvider(requireActivity(), factory)[HospitalViewModel::class.java]

        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.rvHospital.visibility = View.GONE
                    binding.llShimmerContainer.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.rvHospital.visibility = View.VISIBLE
                    binding.llShimmerContainer.visibility = View.GONE
                    binding.tvNoData.visibility = View.GONE
                    (binding.rvHospital.adapter as HospitalAdapter).submitList(result.data)
                }
                is Result.Error -> {
                    binding.rvHospital.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.llShimmerContainer.visibility = View.GONE
                }
            }
            binding.srlHospital.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        val hospitalAdapter = HospitalAdapter()
        binding.rvHospital.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = hospitalAdapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.srlHospital.setOnRefreshListener {
            getMyLocation(refresh = true)
            binding.srlHospital.isRefreshing = false
        }
    }

    private fun getMyLocationIfNeeded() {
        if (viewModel.lat.value != null && viewModel.lon.value != null) {
            viewModel.getHospital(viewModel.lat.value!!, viewModel.lon.value!!, refresh = false)
        } else {
            getMyLocation()
        }
    }

    private fun getMyLocation(refresh: Boolean = false) {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    viewModel.setLatLng(lat, lon)
                    viewModel.getHospital(lat, lon, refresh)
                } else {
                    startLocationUpdates()
                }
            }.addOnFailureListener {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Something went wrong")
                    .setMessage("Failed to get location. Please try again.")
                    .setPositiveButton("Try Again") { dialog, _ ->
                        dialog.dismiss()
                        getMyLocation()
                    }.show()
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> getMyLocation()
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> getMyLocation()
            }
        }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                requireActivity().mainLooper
            )
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
