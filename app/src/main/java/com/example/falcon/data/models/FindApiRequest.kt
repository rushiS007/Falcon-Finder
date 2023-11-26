package com.example.falcon.data.models

import com.google.gson.annotations.SerializedName

data class FindApiRequest(
    val token: String?="",
    @SerializedName("planet_names") val planetNames: List<String>?,
    @SerializedName("vehicle_names") val VehicleNames: List<String>?
)