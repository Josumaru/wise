package com.padangmurah.wise.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padangmurah.wise.data.source.local.entity.auth.RegisterEntity
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.data.source.local.entity.hospital.HospitalEntity
import com.padangmurah.wise.data.source.local.entity.predict.PredictEntity
import com.padangmurah.wise.data.source.local.room.dao.auth.RegisterDao
import com.padangmurah.wise.data.source.local.room.dao.history.HistoryDao
import com.padangmurah.wise.data.source.local.room.dao.hospital.HospitalDao
import com.padangmurah.wise.data.source.local.room.dao.predict.PredictDao

@Database(
    entities = [HistoryEntity::class, HospitalEntity::class, PredictEntity::class, RegisterEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WiseDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun predictDao(): PredictDao
    abstract fun hospitalDao(): HospitalDao
    abstract fun registerDao(): RegisterDao

    companion object {
        @Volatile
        private var INSTANCE: WiseDatabase? = null
        fun getDatabase(context: Context): WiseDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WiseDatabase::class.java,
                    "Wise.db"
                ).build()
            }
    }
}