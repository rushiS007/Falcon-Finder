package com.example.falcon.data.source

import com.example.falcon.data.models.FindApiRequest
import com.example.falcon.data.models.FindApiResponse
import com.example.falcon.data.models.GetPlanetsResponse
import com.example.falcon.data.models.GetPlanetsResponseItem
import com.example.falcon.data.models.GetVehiclesResponseItem
import com.example.falcon.data.models.PostTokenResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitHelper {

    private const val BASE_URL = "https://findfalcone.geektrust.com/"

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    suspend fun getPlanetsApi() : List<GetPlanetsResponseItem> = retrofit.create(FalconApi::class.java).getPlanets()

    suspend fun getVehiclesApi() : List<GetVehiclesResponseItem> = retrofit.create(FalconApi::class.java).getVehicles()

    suspend fun getTokenApi() : PostTokenResponse = retrofit.create(FalconApi::class.java).getToken()

    suspend fun getFindPrincessApi( token:String,
                                    planets: List<String>?,
                                   vehicles: List<String>?) : FindApiResponse = retrofit.create(FalconApi::class.java).findPrincess(
        FindApiRequest(token,planets,vehicles)
    )

}