package com.padangmurah.wise.data.source.local.room.dao.predict

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.padangmurah.wise.data.source.local.entity.predict.PredictEntity

@Dao
interface PredictDao {
    interface HistoryDao {
        @Query("SELECT * FROM predict")
        fun getAllPredict(): LiveData<List<PredictEntity>>
    }
}