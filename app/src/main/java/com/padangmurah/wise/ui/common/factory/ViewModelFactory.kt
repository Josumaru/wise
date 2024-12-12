package com.padangmurah.wise.ui.common.factory


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.padangmurah.wise.data.repository.AuthRepository
import com.padangmurah.wise.data.repository.HistoryRepository
import com.padangmurah.wise.data.repository.HospitalRepository
import com.padangmurah.wise.data.repository.PredictRepository
import com.padangmurah.wise.di.Injection
import com.padangmurah.wise.ui.camera.CameraViewModel
import com.padangmurah.wise.ui.detail.DetailViewModel
import com.padangmurah.wise.ui.history.HistoryViewModel
import com.padangmurah.wise.ui.hospital.HospitalViewModel


class ViewModelFactory(private val authRepository: AuthRepository, private val historyRepository: HistoryRepository, private val predictRepository: PredictRepository, private val hospitalRepository: HospitalRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(historyRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(predictRepository) as T
            }
            modelClass.isAssignableFrom(HospitalViewModel::class.java) -> {
                HospitalViewModel(hospitalRepository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(historyRepository) as T
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
                    INSTANCE = ViewModelFactory(Injection.authInjection(context), Injection.historyInjection(context), Injection.predictInjection(context), Injection.hospitalInjection(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}