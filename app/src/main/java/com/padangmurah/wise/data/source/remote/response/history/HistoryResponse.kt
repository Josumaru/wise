package com.padangmurah.wise.data.source.remote.response.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("treatment")
	val treatment: String? = null,

	@field:SerializedName("diagnosis_id")
	val diagnosisId: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
