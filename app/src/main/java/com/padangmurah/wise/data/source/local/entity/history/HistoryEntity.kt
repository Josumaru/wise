package com.padangmurah.wise.data.source.local.entity.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(

    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "treatment")
    val treatment: String,

    @ColumnInfo(name = "diagnosis_id")
    val diagnosisId: String,

    @ColumnInfo(name = "photo")
    val photo: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String
)
