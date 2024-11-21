package com.padangmurah.wise.data.repository

import com.padangmurah.wise.data.source.local.room.dao.history.HistoryDao
import com.padangmurah.wise.data.source.remote.retrofit.ApiService
import com.padangmurah.wise.util.AppExecutors

class HistoryRepository private constructor(
    private val apiService: ApiService,
    private val historyDao: HistoryDao,
    private val appExecutors: AppExecutors
) {
    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            historyDao: HistoryDao,
            appExecutors: AppExecutors
        ): HistoryRepository {
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(apiService, historyDao, appExecutors)
            }.also { instance = it }
            return instance as HistoryRepository
        }
    }
}