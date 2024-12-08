package com.padangmurah.wise.data.repository


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.padangmurah.wise.data.source.local.room.dao.predict.PredictDao
import com.padangmurah.wise.data.source.remote.response.predict.PredictResponse;
import com.padangmurah.wise.data.source.remote.retrofit.ApiService;
import com.padangmurah.wise.util.Result;
import com.padangmurah.wise.util.AppExecutors;
import okhttp3.MultipartBody;
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class PredictRepository private constructor(
    private val apiService: ApiService,
    private val predictDao: PredictDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<PredictResponse>>()

    fun predict(image: MultipartBody.Part): LiveData<Result<PredictResponse>> {
        result.value = Result.Loading
        val client = apiService.predict(image)
        client.enqueue(object: Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) = if (response.isSuccessful) {
                response.body().let {
                    if(it != null) {
                        result.value = Result.Success(it)
                    }
                }
            } else {
                result.value = Result.Error(response.errorBody()?.string()?: "Unknown error")
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                result.value = Result.Error(t.message?: "Unknown error")
            }
        })
        return result
    }

    companion object {
        @Volatile
        private var instance: PredictRepository? = null
        fun getInstance(
            apiService: ApiService,
            predictDao: PredictDao,
            appExecutors: AppExecutors
        ): PredictRepository {
            instance ?: synchronized(this) {
                instance ?: PredictRepository(apiService, predictDao, appExecutors)
            }.also { instance = it }
            return instance as PredictRepository
        }
    }
}