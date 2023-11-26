package com.example.falcon.data.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetPlanetsResponse(

	@field:SerializedName("GetPlanetsResponse")
	val getPlanetsResponse: List<GetPlanetsResponseItem?>? = null
) : Parcelable

@Parcelize
data class GetPlanetsResponseItem(

	@field:SerializedName("distance")
	val distance: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable
