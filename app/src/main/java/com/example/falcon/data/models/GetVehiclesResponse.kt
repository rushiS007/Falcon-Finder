package com.example.falcon.data.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetVehiclesResponse(

	@field:SerializedName("GetVehiclesResponse")
	val getVehiclesResponse: List<GetVehiclesResponseItem?>? = null
) : Parcelable

@Parcelize
data class GetVehiclesResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("total_no")
	val totalNo: Int? = null,

	@field:SerializedName("max_distance")
	val maxDistance: Int? = null,

	@field:SerializedName("speed")
	val speed: Int? = null
) : Parcelable
