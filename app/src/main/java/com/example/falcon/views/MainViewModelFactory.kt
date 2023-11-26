package com.example.falcon.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.falcon.SharedPreferences
import com.example.falcon.data.repo.FalconRepo

class MainViewModelFactory(private val falconRepo: FalconRepo, private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(falconRepo,sharedPreferences) as T
    }

}