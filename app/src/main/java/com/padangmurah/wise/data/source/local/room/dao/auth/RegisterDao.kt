package com.padangmurah.wise.data.source.local.room.dao.auth

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padangmurah.wise.data.source.local.entity.auth.RegisterEntity
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity

@Dao
interface RegisterDao {
    @Query("SELECT * FROM user")
    fun getAuth(): LiveData<RegisterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAuth(register: RegisterEntity)

    @Query("DELETE FROM user")
    fun deleteAuth()
}