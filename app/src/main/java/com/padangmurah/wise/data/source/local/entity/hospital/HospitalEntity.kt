package com.padangmurah.wise.data.source.local.entity.hospital
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hospital")
data class HospitalEntity
    (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "address")
    val address: String? = null,

    @ColumnInfo(name = "distance")
    val distance: Double? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,
)