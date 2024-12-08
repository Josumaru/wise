package com.padangmurah.wise.data.source.local.entity.hospital

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hospital")
data class HospitalEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double? = null,

    @ColumnInfo(name = "longitude")
    val longitude: Double? = null,

    @ColumnInfo(name = "distance")
    val distance: Double? = null,

    @ColumnInfo(name = "website")
    val website: String? = null,

    @ColumnInfo(name = "phone")
    val phone: String? = null
)