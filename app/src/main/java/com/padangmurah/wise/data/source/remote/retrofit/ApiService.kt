package com.padangmurah.wise.data.source.remote.retrofit

import com.padangmurah.wise.data.source.remote.response.AuthResponse
import com.padangmurah.wise.data.source.remote.response.history.HistoryResponse
import com.padangmurah.wise.data.source.remote.response.hospital.HospitalResponse
import com.padangmurah.wise.data.source.remote.response.predict.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("login")
    fun getPopular(): Call<AuthResponse>

    @Multipart
    @POST("api/v1/predict")
    fun predict(
        @Part image: MultipartBody.Part,
    ): Call<PredictResponse>

    @GET("api/v1/nearby-hospitals")
    fun getNearbyHospitals(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): Call<HospitalResponse>

    @GET("api/v1/history")
    fun getHistory(): Call<HistoryResponse>

}