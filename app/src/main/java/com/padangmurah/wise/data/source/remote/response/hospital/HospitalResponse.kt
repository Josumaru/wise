package com.padangmurah.wise.data.source.remote.response.hospital

import com.google.gson.annotations.SerializedName

data class HospitalResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("contact")
	val contact: Contact? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)

data class Contact(

	@field:SerializedName("website")
	val website: Any? = null,

	@field:SerializedName("phone")
	val phone: Any? = null
)
