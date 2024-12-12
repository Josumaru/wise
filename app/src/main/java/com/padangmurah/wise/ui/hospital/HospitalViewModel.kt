package com.padangmurah.wise.ui.hospital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padangmurah.wise.data.repository.HospitalRepository
import com.padangmurah.wise.data.source.local.entity.hospital.HospitalEntity
import com.padangmurah.wise.util.Result


class HospitalViewModel(private val hospitalRepository: HospitalRepository) : ViewModel() {
    private val _result = MutableLiveData<Result<List<HospitalEntity>>>()
    val result: LiveData<Result<List<HospitalEntity>>> = _result

    private val _lat = MutableLiveData<Double>()
    val lat: LiveData<Double> = _lat

    private val _lon = MutableLiveData<Double>()
    val lon: LiveData<Double> = _lon

    fun setLatLng(lat: Double, lon: Double) {
        _lat.value = lat
        _lon.value = lon
    }

    fun getHospital(lat: Double?, lon: Double?, refresh: Boolean = false) {
        if (_result.value != null && !refresh) return
        hospitalRepository.getHospital(lat, lon).observeForever { result ->
            _result.value = result
        }
    }

}