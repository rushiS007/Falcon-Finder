package com.example.falcon.data.models

import com.google.gson.annotations.SerializedName

data class FindApiResponse (
    @SerializedName("planet_name") val planetName: String?="",
    val status: String?="",
    val error: String?=""
)