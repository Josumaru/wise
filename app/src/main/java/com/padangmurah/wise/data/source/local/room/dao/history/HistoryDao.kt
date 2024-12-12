package com.padangmurah.wise.data.source.local.room.dao.history

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAllHistory(): LiveData<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: List<HistoryEntity>)

    @Query("DELETE FROM history")
    fun deleteAllHistory()
}