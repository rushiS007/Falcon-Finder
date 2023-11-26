package com.example.falcon.data.source

import com.example.falcon.data.models.FindApiRequest
import com.example.falcon.data.models.FindApiResponse
import com.example.falcon.data.models.GetPlanetsResponseItem
import com.example.falcon.data.models.GetVehiclesResponseItem
import com.example.falcon.data.models.PostTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface FalconApi {

    @GET("/planets")
    suspend fun getPlanets():List<GetPlanetsResponseItem>

    @GET("/vehicles")
    suspend fun getVehicles(): List<GetVehiclesResponseItem>

    @Headers("Accept: application/json")
    @POST("/token")
    suspend fun getToken(): PostTokenResponse

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("/find")
    suspend fun findPrincess(@Body body: FindApiRequest): FindApiResponse
}