package com.padangmurah.wise.di

import android.content.Context
import com.padangmurah.wise.data.repository.AuthRepository
import com.padangmurah.wise.data.repository.HistoryRepository
import com.padangmurah.wise.data.repository.HospitalRepository
import com.padangmurah.wise.data.repository.PredictRepository
import com.padangmurah.wise.data.source.local.room.WiseDatabase
import com.padangmurah.wise.data.source.remote.retrofit.ApiConfig
import com.padangmurah.wise.util.AppExecutors

object Injection {

    // Auth Dependency Injection
    fun authInjection (context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        val database = WiseDatabase.getDatabase(context)
        val authDao = database.authDao()
        val appExecutors = AppExecutors()
        return AuthRepository.getInstance(apiService, authDao, appExecutors)
    }

    // History Dependency Injection
    fun historyInjection (context: Context): HistoryRepository {
        val apiService = ApiConfig.getApiService()
        val database = WiseDatabase.getDatabase(context)
        val historyDao = database.historyDao()
        val appExecutors = AppExecutors()
        return HistoryRepository.getInstance(apiService, historyDao, appExecutors)
    }

    // Predict Dependency Injection
    fun predictInjection (context: Context): PredictRepository {
        val apiService = ApiConfig.getApiService()
        val database = WiseDatabase.getDatabase(context)
        val predictDao = database.predictDao()
        val appExecutors = AppExecutors()
        return PredictRepository.getInstance(apiService, predictDao, appExecutors)
    }

    // Hospital Dependency Injection
    fun hospitalInjection (context: Context): HospitalRepository {
        val apiService = ApiConfig.getApiService()
        val database = WiseDatabase.getDatabase(context)
        val hospitalDao = database.hospitalDao()
        val appExecutors = AppExecutors()
        return HospitalRepository.getInstance(apiService, hospitalDao, appExecutors)
    }

}