package com.padangmurah.wise.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.padangmurah.wise.data.source.local.entity.auth.RegisterEntity
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.data.source.local.room.dao.auth.RegisterDao
import com.padangmurah.wise.data.source.local.room.dao.history.HistoryDao
import com.padangmurah.wise.data.source.remote.response.Auth.RegisterResponse
import com.padangmurah.wise.data.source.remote.response.history.HistoryResponse
import com.padangmurah.wise.data.source.remote.retrofit.ApiService
import com.padangmurah.wise.util.AppExecutors
import com.padangmurah.wise.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class RegisterRepository private constructor(
    private val apiService: ApiService,
    private val registerDao: RegisterDao,
    private val appExecutors: AppExecutors
) {

    private val result = MediatorLiveData<Result<RegisterEntity>>()

    fun register(data: RegisterEntity): LiveData<Result<RegisterEntity>> {
        result.value = Result.Loading
        val client = apiService.register(data)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) = if (response.isSuccessful) {
                response.body().let {
                    if (it != null) {
                        appExecutors.diskIO.execute {
                            registerDao.deleteAuth()
                            val registerEntity = RegisterEntity(
                                    password = it.password,
                                    noHp = it.noHp,
                                    name = it.name,
                                    email = it.email,
                                    confirmPassword = it.confirmPassword
                            )

                            registerDao.setAuth(registerEntity)
                        }
                    }
                }
            } else {
                result.value = Result.Error(response.errorBody()?.string() ?: "Unknown error")
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                result.value = Result.Error(t.message ?: "Unknown error")
            }
        })

        val localData = registerDao.getAuth()
        result.addSource(localData) { newData: RegisterEntity ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: RegisterRepository? = null
        fun getInstance(
            apiService: ApiService,
            registerDao : RegisterDao,
            appExecutors: AppExecutors
        ): RegisterRepository {
            instance ?: synchronized(this) {
                instance ?: RegisterRepository(apiService, registerDao, appExecutors)
            }.also { instance = it }
            return instance as RegisterRepository
        }
    }
}