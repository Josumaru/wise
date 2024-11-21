package com.padangmurah.wise.data.source.remote.retrofit

import com.padangmurah.wise.data.source.remote.response.AuthResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("login")
    fun getPopular(): Call<AuthResponse>

}