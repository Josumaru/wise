package com.padangmurah.wise.data.source.local.room.dao.auth

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padangmurah.wise.data.source.local.entity.auth.AuthEntity
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity

@Dao
interface AuthDao {
    @Query("SELECT * FROM user")
    fun getAuth(): LiveData<AuthEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAuth(history: HistoryEntity)

    @Query("DELETE FROM user")
    fun deleteAuth()
}