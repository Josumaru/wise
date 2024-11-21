package com.padangmurah.wise.data.repository

import com.padangmurah.wise.data.source.local.room.dao.auth.AuthDao
import com.padangmurah.wise.data.source.remote.retrofit.ApiService
import com.padangmurah.wise.util.AppExecutors

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val authDao: AuthDao,
    private val appExecutors: AppExecutors
) {
    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            authDao: AuthDao,
            appExecutors: AppExecutors
        ): AuthRepository {
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, authDao, appExecutors)
            }.also { instance = it }
            return instance as AuthRepository
        }
    }
}