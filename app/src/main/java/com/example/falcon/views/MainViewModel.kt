package com.example.falcon.views

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.falcon.SharedPreferences
import com.example.falcon.data.models.FindApiResponse
import com.example.falcon.data.models.GetPlanetsResponseItem
import com.example.falcon.data.models.GetVehiclesResponseItem
import com.example.falcon.data.repo.FalconRepo
import kotlinx.coroutines.launch

class MainViewModel constructor(private val falconRepo: FalconRepo,private val sharedPreferencesManager: SharedPreferences) : ViewModel() {


    private val _planets = mutableStateListOf<GetPlanetsResponseItem>()
    val planets : List<GetPlanetsResponseItem>
        get() = _planets

    private val _rockets = mutableStateListOf<GetVehiclesResponseItem>()
    val rockets : List<GetVehiclesResponseItem>
        get() = _rockets

    private val _findResponse = mutableStateOf<FindApiResponse>(FindApiResponse())
    val findResponse =  _findResponse

    private val _selectedList = mutableStateListOf<Pair<String,String>>()
    val selectedList = _selectedList

    fun getPlanets(){
        viewModelScope.launch {
            val data = falconRepo.getPlanets()
            _planets.clear()
            _planets.addAll(data)
            println(data)
        }
    }

    fun getRockets(){
        viewModelScope.launch {
            val data = falconRepo.getVehicles()
            _rockets.clear()
            _rockets.addAll(data)
            println(data)
        }
    }

     private suspend fun getToken(){
        viewModelScope.launch {
            val data = falconRepo.getToken()
            sharedPreferencesManager.saveString("token",data.token.toString())
        }
    }

    fun removeLastItem(){
        if(selectedList.isNotEmpty())
            selectedList.removeLast()
    }

    fun findPrincess(planets: List<String>?, vehicles: List<String>?){
        viewModelScope.launch{
            getToken()
            val token = sharedPreferencesManager.getString("token")
            if(token.isNotEmpty()) {
                println("finding princess")
                val found = falconRepo.findPrincess(
                    token,
                    planets,
                    vehicles
                )
                _findResponse.value = found
                _selectedList.clear()
                println("$found data")
            }
        }
    }

}