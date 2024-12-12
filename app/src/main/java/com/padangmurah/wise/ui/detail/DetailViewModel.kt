package com.padangmurah.wise.ui.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padangmurah.wise.data.repository.PredictRepository
import com.padangmurah.wise.data.source.remote.response.predict.PredictResponse
import okhttp3.MultipartBody
import com.padangmurah.wise.util.Result

class DetailViewModel(private val predictRepository: PredictRepository) : ViewModel() {
    private val _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri> = _uri

    private val _result = MutableLiveData<Result<PredictResponse>>()
    val result: LiveData<Result<PredictResponse>> = _result

    fun setUri(uri: Uri) {
        _uri.value = uri
    }

    fun predict(image: MultipartBody.Part) {
        if(_result.value != null) {
            return;
        }
        predictRepository.predict(image).observeForever{ result ->
            _result.value = result
        }
    }
}