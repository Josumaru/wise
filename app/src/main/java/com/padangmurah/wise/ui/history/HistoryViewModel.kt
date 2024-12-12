package com.padangmurah.wise.ui.history

import androidx.lifecycle.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.padangmurah.wise.data.repository.HistoryRepository
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.util.Result


class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    private val _result = MutableLiveData<Result<List<HistoryEntity>>>()
    val result: LiveData<Result<List<HistoryEntity>>> = _result

    fun getHistory(refresh: Boolean = false) {
        if(_result.value != null && !refresh) return
        historyRepository.getHistory().observeForever { result ->
            _result.value = result
        }
    }

}