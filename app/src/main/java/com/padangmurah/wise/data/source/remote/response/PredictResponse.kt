package com.padangmurah.wise.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Details(

	@field:SerializedName("treatment")
	val treatment: String? = null,

	@field:SerializedName("class_name")
	val className: String? = null
)

data class Data(

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("details")
	val details: Details? = null
)
