package com.padangmurah.wise.data.source.local.entity.auth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class AuthEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,
)