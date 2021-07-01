package com.example.fanfics.ui.fanfic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fanfics.App
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.models.Chapter
import com.example.fanfics.data.models.Fanfic
import retrofit2.HttpException
import java.io.IOException

class FanficViewModel(val apiService: ApiService = App.appComponent.getApiService()): ViewModel() {

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters: LiveData<List<Chapter>> = _chapters


    suspend fun loadChapters(fanficId: Long): Boolean{
        return try {
            _chapters.postValue(apiService.getChapters(fanficId))
            true
        } catch (e: IOException){
            false
        } catch (e: HttpException){
            false
        }
    }

    val readEvent = MutableLiveData<String>()

}