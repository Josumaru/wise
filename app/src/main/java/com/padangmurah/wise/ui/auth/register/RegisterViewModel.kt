package com.padangmurah.wise.ui.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padangmurah.wise.data.repository.PredictRepository
import com.padangmurah.wise.data.repository.RegisterRepository
import com.padangmurah.wise.data.source.local.entity.auth.RegisterEntity
import com.padangmurah.wise.data.source.remote.response.predict.PredictResponse
import okhttp3.MultipartBody
import com.padangmurah.wise.util.Result

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {
    private val _user = MutableLiveData<Result<RegisterEntity>>()
    val user: LiveData<Result<RegisterEntity>> = _user

    fun setUserData(user: RegisterEntity) {
        _user.value = user
    }
    fun register(data: RegisterEntity) {
        if(_user.value != null) {
            return;
        }
        registerRepository.register(data).observeForever{ result ->
            _user.value = result
        }
    }
}