package com.padangmurah.wise.data.source.local.entity.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,
)