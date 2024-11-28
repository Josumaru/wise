package com.padangmurah.wise.ui.common.factory


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.padangmurah.wise.data.repository.AuthRepository
import com.padangmurah.wise.data.repository.HistoryRepository
import com.padangmurah.wise.di.Injection
import com.padangmurah.wise.ui.camera.CameraViewModel


class ViewModelFactory(private val authRepository: AuthRepository, private val historyRepository: HistoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(historyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.authInjection(context), Injection.historyInjection(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}