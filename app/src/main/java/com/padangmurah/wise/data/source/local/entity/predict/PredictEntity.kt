package com.padangmurah.wise.data.source.local.entity.predict

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "predict")
data class PredictEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
)