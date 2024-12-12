package com.padangmurah.wise.data.source.local.entity.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class RegisterEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val password: String? = null,

    val noHp: String? = null,

    val name: String? = null,

    val email: String? = null,

    val confirmPassword: String? = null
)