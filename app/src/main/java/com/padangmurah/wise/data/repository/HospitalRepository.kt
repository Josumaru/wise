package com.padangmurah.wise.data.repository

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.padangmurah.wise.data.source.local.entity.hospital.HospitalEntity
import com.padangmurah.wise.data.source.local.room.dao.hospital.HospitalDao
import com.padangmurah.wise.data.source.remote.response.hospital.HospitalResponse
import com.padangmurah.wise.data.source.remote.retrofit.ApiService;
import com.padangmurah.wise.util.Result;
import com.padangmurah.wise.util.AppExecutors;
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.util.ArrayList

class HospitalRepository private constructor(
    private val apiService: ApiService,
    private val hospitalDao: HospitalDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<HospitalEntity>>>()

    fun getHospital(lat: Double?, lon: Double?): LiveData<Result<List<HospitalEntity>>> {
        result.value = Result.Loading

        val localData = hospitalDao.getAllHospitals()
        result.addSource(localData) { newData: List<HospitalEntity> ->
            if (lat == null || lon == null || newData.isNotEmpty()) {
                result.value = Result.Success(newData)
            }
        }

        if (lat != null && lon != null) {
            val client = apiService.getNearbyHospitals(lat, lon)
            client.enqueue(object : Callback<HospitalResponse> {
                override fun onResponse(
                    call: Call<HospitalResponse>,
                    response: Response<HospitalResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            appExecutors.diskIO.execute {
                                hospitalDao.deleteAllHospitals()
                                val hospitalList = it.data?.mapIndexed { index, hospital ->
                                    HospitalEntity(
                                        id = index,
                                        name = hospital?.name ?: "",
                                        latitude = hospital?.latitude ?: 0.0,
                                        longitude = hospital?.longitude ?: 0.0,
                                        distance = hospital?.distance ?: 0.0,
                                        website = hospital?.contact?.website.toString(),
                                        phone = hospital?.contact?.phone.toString(),
                                    )
                                } ?: emptyList()
                                hospitalDao.insertHospitals(hospitalList.sortedBy { hospital -> hospital.distance })
                            }
                        }
                    } else {
                        result.value = Result.Error(response.errorBody()?.string() ?: "Unknown error")
                    }
                }

                override fun onFailure(call: Call<HospitalResponse>, t: Throwable) {
                    result.value = Result.Error(t.message ?: "Unknown error")
                }
            })
        }

        return result
    }


    companion object {
        @Volatile
        private var instance: HospitalRepository? = null
        fun getInstance(
            apiService: ApiService,
            hospitalDao: HospitalDao,
            appExecutors: AppExecutors
        ): HospitalRepository {
            instance ?: synchronized(this) {
                instance ?: HospitalRepository(apiService, hospitalDao, appExecutors)
            }.also { instance = it }
            return instance as HospitalRepository
        }
    }
}