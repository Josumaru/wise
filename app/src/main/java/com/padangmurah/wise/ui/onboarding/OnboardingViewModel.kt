package com.padangmurah.wise.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardingViewModel: ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _page = MutableLiveData<Int>().apply { value = 1 }
    val page: LiveData<Int> = _page

    fun setPage(page: Int) {
        _page.value = page
    }
}