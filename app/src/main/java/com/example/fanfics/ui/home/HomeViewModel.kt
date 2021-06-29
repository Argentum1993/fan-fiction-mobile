package com.example.fanfics.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fanfics.App
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.models.Fanfic
import kotlinx.coroutines.launch

class HomeViewModel constructor(val apiService: ApiService = App.appComponent.getApiService()):
    ViewModel() {

    companion object{
        private const val MAX_ITEMS = 10
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _recommendedFanfics = MutableLiveData<List<Fanfic>>()
    val recommendedFanfics: LiveData<List<Fanfic>> = _recommendedFanfics

    init {
        viewModelScope.launch {
            _recommendedFanfics.postValue(apiService.getRecommendedFanfics(App.user.id, MAX_ITEMS))
        }
    }

    private val _randomFanfics = MutableLiveData<List<Fanfic>>()
    val randomFanfics: LiveData<List<Fanfic>> = _randomFanfics

    init {
        viewModelScope.launch {
            _randomFanfics.postValue(apiService.getRandomFanfics(MAX_ITEMS))
        }
    }

    private val _currentRandomFanfic = MutableLiveData<Fanfic>()
    val currentRandomFanfic: LiveData<Fanfic> = _currentRandomFanfic

    private val _currentRecommendedFanfic = MutableLiveData<Fanfic>()
    val currentRecommendedFanfic: LiveData<Fanfic> = _currentRecommendedFanfic

    fun setCurrentRecommendedFanfic(fanfic: Fanfic){
        _currentRecommendedFanfic.postValue(fanfic)
    }

    fun setCurrentRandomFanfic(fanfic: Fanfic){
        _currentRandomFanfic.postValue(fanfic)
    }
}