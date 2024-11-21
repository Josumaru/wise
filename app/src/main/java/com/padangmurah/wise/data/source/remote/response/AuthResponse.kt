package com.padangmurah.wise.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("id")
    val code: String? = null
)