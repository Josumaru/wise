package com.padangmurah.wise.data.source.local.room.dao.hospital

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padangmurah.wise.data.source.local.entity.hospital.HospitalEntity

@Dao
interface HospitalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHospitals(hospitals: List<HospitalEntity>)

    @Query("SELECT * FROM hospital")
    fun getAllHospitals(): LiveData<List<HospitalEntity>>

    @Query("DELETE FROM hospital")
    fun deleteAllHospitals()
}