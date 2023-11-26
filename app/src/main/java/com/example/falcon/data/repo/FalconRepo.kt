package com.example.falcon.data.repo

import com.example.falcon.data.models.FindApiRequest
import com.example.falcon.data.models.FindApiResponse
import com.example.falcon.data.models.GetPlanetsResponse
import com.example.falcon.data.models.GetPlanetsResponseItem
import com.example.falcon.data.models.GetVehiclesResponseItem
import com.example.falcon.data.models.PostTokenResponse
import com.example.falcon.data.source.RetrofitHelper

class FalconRepo {

    suspend fun getPlanets():List<GetPlanetsResponseItem>{
        return RetrofitHelper.getPlanetsApi()
    }

    suspend fun getVehicles():List<GetVehiclesResponseItem>{
        return RetrofitHelper.getVehiclesApi()
    }

    suspend fun getToken():PostTokenResponse{
        return RetrofitHelper.getTokenApi()
    }

    suspend fun findPrincess(token:String, planets: List<String>?, vehicles: List<String>?):FindApiResponse{
        return RetrofitHelper.getFindPrincessApi(token,planets,vehicles)
    }


}