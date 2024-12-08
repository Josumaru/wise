package com.padangmurah.wise.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.data.source.local.room.dao.history.HistoryDao
import com.padangmurah.wise.data.source.remote.response.history.HistoryResponse
import com.padangmurah.wise.data.source.remote.retrofit.ApiService
import com.padangmurah.wise.util.AppExecutors
import com.padangmurah.wise.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HistoryRepository private constructor(
    private val apiService: ApiService,
    private val historyDao: HistoryDao,
    private val appExecutors: AppExecutors
) {

    private val result = MediatorLiveData<Result<List<HistoryEntity>>>()

    fun getHistory(): LiveData<Result<List<HistoryEntity>>> {
        result.value = Result.Loading
        val client = apiService.getHistory()
        client.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) = if (response.isSuccessful) {
                response.body().let {
                    if (it != null) {
                        appExecutors.diskIO.execute {
                            historyDao.deleteAllHistory()
                            val historyList = ArrayList<HistoryEntity>()
                            it.data?.forEach { history ->
                                val historyEntity = HistoryEntity(
                                    id = history?.id ?: "",
                                    treatment = history?.treatment ?: "",
                                    diagnosisId = history?.diagnosisId ?: "",
                                    photo = history?.photo ?: "",
                                    createdAt = history?.createdAt ?: ""
                                )
                                historyList.add(historyEntity)
                            }

                            historyDao.insertHistory(historyList)
                        }
                    }
                }
            } else {
                result.value = Result.Error(response.errorBody()?.string() ?: "Unknown error")
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                result.value = Result.Error(t.message ?: "Unknown error")
            }
        })

        val localData = historyDao.getAllHistory()
        result.addSource(localData) { newData: List<HistoryEntity> ->
            val historyEntityList = ArrayList<HistoryEntity>()
            newData.forEach {
                historyEntityList.add(it)
            }
            result.value = Result.Success(historyEntityList)
        }
        return result
    }

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